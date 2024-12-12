package com.acnovate.auditmanager.service;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class ExcelFileWatcher {
	public static final Logger log = LoggerFactory.getLogger(AuditReportService.class);
	@Value(value = "${dummy.data.excellocation}")
	private String dummyDataExcellocation;
	private final Map<Integer, String> previousState = new HashMap<>();
	private final ExecutorService executor = Executors.newSingleThreadExecutor();
	private volatile boolean isFileBeingProcessed = false;

	@Autowired
	private AuditReportService auditReportService;

	@PostConstruct
	public void startWatching() {
		try {
			Path filePath = Paths.get(dummyDataExcellocation).getParent(); // Directory containing the file
			WatchService watchService = FileSystems.getDefault().newWatchService();
			filePath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

			log.info("Watching directory: {}", filePath);
			processFileChanges(dummyDataExcellocation,true);
			while (true) {
				WatchKey key = watchService.take(); // Wait for a file change event

				for (WatchEvent<?> event : key.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();

					// Ensure it is the target file
					Path changedFile = (Path) event.context();
					if (kind == StandardWatchEventKinds.ENTRY_MODIFY
							&& changedFile.toString().equals(new File(dummyDataExcellocation).getName())) {
						log.info("Modification detected for: {}", changedFile);

						// Delay processing to ensure the file is closed
						if (!isFileBeingProcessed) {
							isFileBeingProcessed = true;
							executor.submit(() -> {
								try {
									TimeUnit.SECONDS.sleep(5); // Wait for modifications to finish
									processFileChanges(dummyDataExcellocation,false);
								} catch (InterruptedException e) {
									Thread.currentThread().interrupt();
								} finally {
									isFileBeingProcessed = false;
								}
							});
						}
					}
				}

				if (!key.reset()) {
					break; // Exit if the key is no longer valid
				}
			}
		} catch (Exception e) {
			log.error("Error while startWatching::Exeception ::{}", ExceptionUtils.getStackTrace(e));
		}
	}

	private void processFileChanges(String filePath,boolean initial) {
		try {
			// Check if the file is accessible and locked
			boolean isFileAccessible = false;
			while (!isFileAccessible) {
				try (FileInputStream fis = new FileInputStream(filePath);
						java.nio.channels.FileChannel channel = fis.getChannel()) {

					// Try to acquire an exclusive lock on the file
					java.nio.channels.FileLock lock = channel.tryLock(0, Long.MAX_VALUE, true);
					if (lock != null) {
						isFileAccessible = true;
						lock.release(); // Release the lock once acquired
					}
				} catch (Exception e) {
					System.out.println("File is still being modified, waiting...");
					TimeUnit.SECONDS.sleep(1); // Wait and retry
				}
			}

			// Proceed with processing the file
			try (FileInputStream fis = new FileInputStream(new File(filePath));
					Workbook workbook = new XSSFWorkbook(fis)) {

				Sheet sheet = workbook.getSheetAt(0); // Monitor the first sheet
				Map<Integer, String> currentState = new HashMap<>();
				LinkedHashMap<String, String> rowMap = new LinkedHashMap<>();
				Iterator<Row> rowIterator = sheet.iterator();
				Row headerRow = rowIterator.next(); // Get the header row
				List<String> keys = new ArrayList<>();

				for (Cell cell : headerRow) {
					keys.add(cell.toString());
				}
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					int rowIndex = row.getRowNum();
					if (rowIndex == 0) {
						continue;
					}
					StringBuilder rowData = new StringBuilder();

					for (int i = 0; i < keys.size(); i++) {
						Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
						String cellValue = FileReader.getCellValue(cell);
						rowData.append(cellValue).append("|");
						rowMap.put(keys.get(i), cellValue); // Add key-value pair
					}

					String rowContent = rowData.toString();
					currentState.put(rowIndex, rowContent);

					// Detect new or updated rows
					if (!initial &&!rowContent.equals(previousState.get(rowIndex))) {
						System.out.println("Change detected at row: " + rowIndex + ", Content: " + rowMap);
						auditReportService.publishChangedData(rowMap);
					}
				}

				// Detect deleted rows
				for (Integer rowIndex : previousState.keySet()) {
					if (!currentState.containsKey(rowIndex)) {
						System.out.println("Row deleted: " + rowIndex);
					}
				}

				// Update the state
				previousState.clear();
				previousState.putAll(currentState);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
