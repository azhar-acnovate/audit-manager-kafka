package com.acnovate.auditmanager.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BulkDataStore {

	@Value("${bulk.data-store.filepath}")
	private String filePath;

	private ObjectMapper objectMapper = new ObjectMapper();

	// Create or overwrite the JSON file
	public void createJsonFile(Map<String, ?> data) throws IOException {
		objectMapper.writeValue(new File(filePath), data);
	}

	// Read data from the JSON file
	public Map<String, String> readJsonFile() {
		File file = new File(filePath);
		try {
			if (!file.exists()) {
				throw new IOException("File not found: " + filePath);
			}
			return objectMapper.readValue(file, new TypeReference<Map<String, String>>() {
			});
		} catch (Exception e) {
			return null;
		}

	}

	// Update the JSON file
	public void updateJsonFile(Map<String, String> updates) {
		Map<String, String> existingData = readJsonFile();
		existingData.putAll(updates); // Merge updates with existing data
		try {
			createJsonFile(existingData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Overwrite file with updated data
	}
}
