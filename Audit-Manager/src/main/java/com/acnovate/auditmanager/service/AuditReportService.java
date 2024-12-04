package com.acnovate.auditmanager.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.acnovate.auditmanager.KafkaProducer;
import com.acnovate.auditmanager.dto.AuditEvent;
import com.acnovate.auditmanager.dto.AuditEventMetadata;
import com.acnovate.auditmanager.dto.AuditEventPayload;
import com.google.gson.Gson;

@Service
public class AuditReportService {
	public static final Logger log = LoggerFactory.getLogger(AuditReportService.class);

	@Value(value = "${dummy.data.excellocation}")
	private String dummyDataExcellocation;

	@Autowired
	private KafkaProducer kafkaProducer;

	@Value(value = "${kafka.producer.topic.auditreport.update}")
	private String kafkaProducerTopicAuditReportUpdate;

	@Value(value = "${kafka.producer.topic.auditreport.update.partitions}")
	private Integer auditreportUpdatePartitions;

	@Autowired
	private BulkDataStore bulkDataStore;

	private Gson gson = new Gson();

	public static final String OBJECT_NAME = "AUDIT_OBJECT_CHANGE_TRACKER";

	// Define the date format based on the input string
	SimpleDateFormat eventOccurenceFomatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");

	public void convertAndPublishAuditReport() {
		ArrayList<HashMap<String, String>> rootElement = FileReader.readExcelFile(dummyDataExcellocation);
		log.info("AuditReportService - convertAndPublishAuditReport - start");
		final StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		for (HashMap<String, String> map : rootElement) {
			String branchId = map.get("REF_OBJECT_ID");
			String key = branchId + OBJECT_NAME;
			Integer hashCode = key.hashCode() & Integer.MAX_VALUE;
			log.info("Partition value :{}", hashCode % 10);
			AuditEvent auditEvent = new AuditEvent();
			AuditEventMetadata metadata = kafkaProducer.setMetadata(OBJECT_NAME.toUpperCase());
			AuditEventPayload payload = kafkaProducer.setPayload(OBJECT_NAME, map);
			auditEvent.setMetadata(metadata);
			auditEvent.setPayload(payload);
			String eventPayload = gson.toJson(auditEvent);
			log.info("Value of PayLoad : {}", eventPayload);
			kafkaProducer.send(kafkaProducerTopicAuditReportUpdate, hashCode % auditreportUpdatePartitions, key,
					eventPayload);
		}
		stopWatch.stop();
		log.info("Total time to process {} events is {} ms", rootElement.size(), stopWatch.getTotalTimeMillis());
		log.info("AuditReportService - convertAndPublishAuditReport - end");
	}

	public void publishBulkData() {

		ArrayList<HashMap<String, String>> rootElement = FileReader.readExcelFile(dummyDataExcellocation);
		log.info("AuditReportService - convertAndPublishAuditReport - start");
		Map<String, String> bulkDataStoreData = bulkDataStore.readJsonFile();
		final StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		for (HashMap<String, String> map : rootElement) {

			String bulkDataKey = map.get("source_reference_name") + "_" + map.get("source_reference_key");

			String branchId = bulkDataStoreData.get(bulkDataKey);

			String key = branchId + OBJECT_NAME;
			Integer hashCode = key.hashCode() & Integer.MAX_VALUE;
			log.info("Partition value :{}", hashCode % 10);
			AuditEvent auditEvent = new AuditEvent();
			AuditEventMetadata metadata = kafkaProducer.setMetadata(OBJECT_NAME.toUpperCase());
			AuditEventPayload payload = kafkaProducer.setPayload(OBJECT_NAME, map);

			formatData(payload, branchId, map.get("source_reference_key"));
			auditEvent.setMetadata(metadata);
			auditEvent.setPayload(payload);
			String eventPayload = gson.toJson(auditEvent);
			log.info("Value of PayLoad : {}", eventPayload);
			kafkaProducer.send(kafkaProducerTopicAuditReportUpdate, hashCode % auditreportUpdatePartitions, key,
					eventPayload);
		}

		// Loop through the map and increment all values
		for (Map.Entry<String, String> entry : bulkDataStoreData.entrySet()) {
			String key = entry.getKey();
			String currentValue = entry.getValue();

			try {
				// Parse the current value as an integer and increment by 1
				int incrementedValue = Integer.parseInt(currentValue) + 1;

				// Update the map with the new value
				bulkDataStoreData.put(key, String.valueOf(incrementedValue));

				// Log the updated value
				log.info("Updated value for {}: {}", key, incrementedValue);
			} catch (NumberFormatException e) {
				log.error("Invalid number format for key {}: {}", key, currentValue);
			}
		}
		bulkDataStore.updateJsonFile(bulkDataStoreData);
		stopWatch.stop();
		log.info("Total time to process {} events is {} ms", rootElement.size(), stopWatch.getTotalTimeMillis());
		log.info("AuditReportService - convertAndPublishAuditReport - end");

	}

	private AuditEventPayload formatData(AuditEventPayload payload, String newId, String currentId) {
		HashMap<String, String> newHashMap = new HashMap<>();
		String currentDate = eventOccurenceFomatter.format(new Date());
		for (Entry<String, String> entry : payload.getDataMap().entrySet()) {
			switch (entry.getKey()) {
			case "source_reference_key": {
				newHashMap.put(entry.getKey(), newId);
				break;
			}
			case "event_occurence": {
				newHashMap.put(entry.getKey(), currentDate);
				break;
			}

			case "old_value", "new_value", "changed_by":
				if (currentId.equals(entry.getValue())) {
					newHashMap.put(entry.getKey(), newId);
				} else {
					newHashMap.put(entry.getKey(), entry.getValue() + "_" + newId);
				}

				break;
			default:
				newHashMap.put(entry.getKey(), entry.getValue());
				break;
			// throw new IllegalArgumentException("Unexpected value: " + entry.getKey());
			}

		}
		payload.setDataMap(newHashMap);
		return payload;
	}

}
