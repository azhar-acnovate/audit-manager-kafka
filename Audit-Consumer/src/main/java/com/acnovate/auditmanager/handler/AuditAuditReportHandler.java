package com.acnovate.auditmanager.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.acnovate.auditmanager.dao.AuditAttributeChangeTrackerRepository;
import com.acnovate.auditmanager.dao.AuditObjectChangeTrackerRepository;
import com.acnovate.auditmanager.dao.SourceReferenceObjectRepository;
import com.acnovate.auditmanager.domain.dto.event.AuditEvent;
import com.acnovate.auditmanager.domain.dto.event.AuditEventPayload;
import com.acnovate.auditmanager.domain.entity.AuditAttributeChangeTracker;
import com.acnovate.auditmanager.domain.entity.AuditObjectChangeTracker;
import com.acnovate.auditmanager.domain.entity.SourceReferenceObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuditAuditReportHandler implements MessageHandler {
	private static final Logger logger = LoggerFactory.getLogger(AuditAuditReportHandler.class);
	private static final StopWatch stopWatch = new StopWatch();
	public static boolean TRANSACTION_ROLLBACK = false;
	private static HashMap<String, String> columnMap = new HashMap<String, String>();

	@Autowired
	private AuditObjectChangeTrackerRepository auditObjectChangeTrackerRepository;

	@Autowired
	private SourceReferenceObjectRepository sourceReferenceObjectRepository;

	@Autowired
	private AuditAttributeChangeTrackerRepository auditAttributeChangeTrackerRepository;

	// Define the date format based on the input string
	SimpleDateFormat eventOccurenceFomatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
	// Create ObjectMapper instance
	ObjectMapper mapper = new ObjectMapper();

	@Override
	public String handle(AuditEvent event, Map<String, Object> headers) {
		try {
			AuditEventPayload payload = event.getPayload();
			processAndLoadAuditReport(payload.getDataMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean isSupported(String eventName) {
		return "AUDIT_OBJECT_CHANGE_TRACKER".equals(eventName.toUpperCase());
	}

	public void processAndLoadAuditReport(final HashMap<String, String> map) throws Exception {
		logger.info("Value of map : " + map);
		logger.info("Size of map : " + map.size());
//		logger.info("Mode: " + mode);
//		if ("FR".equalsIgnoreCase(mode)) {
		processAndLoadAuditReportFR(map);
//		}
	}

	public boolean isFieldNameAvailable(String json, String fieldName) {
		logger.info("json::{}", json);
		try {

			// Parse JSON into JsonNode
			JsonNode rootNode = mapper.readTree(json);
			logger.info("rootNode::{}", rootNode);
			// Check if the specified fieldName is available
			for (JsonNode node : rootNode) {
				logger.info("has fieldName::{}", node.has("fieldName"));
				logger.info("fieldName::{}", fieldName.equals(node.get("fieldName").asText()));
				if (node.has("fieldName") && fieldName.equals(node.get("fieldName").asText())) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Transactional
	public void processAndLoadAuditReportFR(final HashMap<String, String> map) throws Exception {
		if (map == null) {
			logger.warn("Provided map is null. Exiting processAndLoadAuditReportFR.");
			return;
		}
		Long refObjectId = null;
		String sourceReferenceName = map.get("source_reference_name");
		String sourceReferenceKey = map.get("source_reference_key");
		String attributeName = map.get("attribute_name");
		String eventType = map.get("event_type");
		Date eventOccurence = eventOccurenceFomatter.parse(map.get("event_occurence"));
		String oldValue = "";
		String newValue = map.get("new_value");
		String changedBy = map.get("changed_by");
		Optional<SourceReferenceObject> optionalSourceReferenceObject = sourceReferenceObjectRepository
				.findBySourceReferenceNameAndSourceReferenceKey(sourceReferenceName, sourceReferenceKey);

		if (optionalSourceReferenceObject.isPresent()) {
			SourceReferenceObject sourceReferenceObject = optionalSourceReferenceObject.get();
			logger.info("sourceReferenceObject::{}", sourceReferenceObject);
			refObjectId = sourceReferenceObject.getId();
			if (sourceReferenceObject.getAdditionalInfo() != null
					&& isFieldNameAvailable(sourceReferenceObject.getAdditionalInfo(), attributeName)) {
				AuditObjectChangeTracker auditObjectChangeTracker = auditObjectChangeTrackerRepository
						.findByRefObjectIdAndEventTypeAndEventOccurence(refObjectId, eventType, eventOccurence);
				AuditAttributeChangeTracker auditAttributeChangeTracker = new AuditAttributeChangeTracker();
				if (auditObjectChangeTracker == null) {

					auditObjectChangeTracker = new AuditObjectChangeTracker();
					auditObjectChangeTracker.setEventOccurence(eventOccurence);
					auditObjectChangeTracker.setEventType(eventType);
					auditObjectChangeTracker.setRefObjectId(refObjectId);
					auditObjectChangeTracker = auditObjectChangeTrackerRepository.save(auditObjectChangeTracker);
				} 
				AuditAttributeChangeTracker attributeNameLastChanges = auditAttributeChangeTrackerRepository
						.findTop1ByAttributeNameAndAuditObjectChangeTrackerRefObjectIdOrderByAuditObjectChangeTrackerUpdatedAtDesc(
								attributeName, refObjectId);

				if (attributeNameLastChanges != null) {
					oldValue = attributeNameLastChanges.getNewValue() != null
							? attributeNameLastChanges.getNewValue()
							: "";
				}

				auditAttributeChangeTracker.setAttributeName(attributeName);
				auditAttributeChangeTracker.setOldValue(oldValue);
				auditAttributeChangeTracker.setNewValue(newValue);
				auditAttributeChangeTracker.setChangedBy(changedBy);
				auditAttributeChangeTracker.setAuditObjectChangeTracker(auditObjectChangeTracker);
				// LocalDateTime currentDateTime = LocalDateTime.now();

				auditAttributeChangeTrackerRepository.save(auditAttributeChangeTracker);
			} else {
				logger.warn("Soruce reference {} attributeName is not available in audit system with {},{}",
						attributeName, sourceReferenceName, sourceReferenceKey);
			}

		} else {
			logger.warn("Soruce reference not available in audit system with {},{}", sourceReferenceName,
					sourceReferenceKey);
		}

	}
}