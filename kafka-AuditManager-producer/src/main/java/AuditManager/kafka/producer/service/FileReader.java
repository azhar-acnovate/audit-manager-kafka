package AuditManager.kafka.producer.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FileReader {

    public static ArrayList<HashMap<String, String>> readExcelFile(String filePath) {
        ArrayList<HashMap<String, String>> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Get first sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);

            // Get the header row
            Row headerRow = sheet.getRow(0);
            String[] headers = new String[headerRow.getPhysicalNumberOfCells()];

            for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
                headers[i] = headerRow.getCell(i).getStringCellValue();
            }

            // Loop through each row in the sheet starting from the second row
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                HashMap<String, String> dataMap = new HashMap<>();

                for (int cellIndex = 0; cellIndex < headers.length; cellIndex++) {
                    Cell cell = row.getCell(cellIndex);
                    String cellValue = getCellValue(cell);
                    dataMap.put(headers[cellIndex], cellValue); // Add key-value pairs
                }

                dataList.add(dataMap); // Add the map to the list
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error as per your application's requirements
            return null; // Or throw an exception
        }

        return dataList; // Return the ArrayList of HashMaps
    }

    // Helper method to get cell value as a string
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString(); // Format dates as needed
                } else {
                    return String.valueOf((long) cell.getNumericCellValue()); // Convert numeric value to long
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
