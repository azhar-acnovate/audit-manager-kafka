package com.acnovate.kafka.consumer.AuditManager.handler;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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

import com.acnovate.kafka.consumer.AuditManager.dao.AuditAttributeChangeTrackerRepository;
import com.acnovate.kafka.consumer.AuditManager.dao.AuditObjectChangeTrackerRepository;
import com.acnovate.kafka.consumer.AuditManager.dao.SourceReferenceObjectRepository;
import com.acnovate.kafka.consumer.AuditManager.domain.dto.event.AuditEvent;
import com.acnovate.kafka.consumer.AuditManager.domain.dto.event.AuditEventPayload;
import com.acnovate.kafka.consumer.AuditManager.domain.entity.AuditAttributeChangeTracker;
import com.acnovate.kafka.consumer.AuditManager.domain.entity.AuditObjectChangeTracker;
import com.acnovate.kafka.consumer.AuditManager.domain.entity.SourceReferenceObject;

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

	@Override
	public String handle(AuditEvent event, Map<String, Object> headers) {
		try {
			AuditEventPayload payload = event.getPayload();
			processAndLoadAuditReport(payload.getDataMap(), payload.getMode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean isSupported(String eventName) {
		return "AUDIT_OBJECT_CHANGE_TRACKER".equals(eventName.toUpperCase());
	}

	public void processAndLoadAuditReport(final HashMap<String, String> map, String mode) throws Exception {
		logger.info("Value of map : " + map);
		logger.info("Size of map : " + map.size());
		logger.info("Mode: " + mode);
		if ("FR".equalsIgnoreCase(mode)) {
			processAndLoadAuditReportFR(map);
		}
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
		String oldValue = map.get("old_value");
		String newValue = map.get("new_value");
		String changedBy = map.get("changed_by");
		Optional<SourceReferenceObject> optionalSourceReferenceObject = sourceReferenceObjectRepository
				.findBySourceReferenceNameAndSourceReferenceKey(sourceReferenceName, sourceReferenceKey);

		if (optionalSourceReferenceObject.isPresent()) {
			SourceReferenceObject sourceReferenceObject = optionalSourceReferenceObject.get();
			refObjectId = sourceReferenceObject.getId();
		} else {
			SourceReferenceObject sourceReferenceObject = new SourceReferenceObject();
			sourceReferenceObject.setSourceReferenceName(sourceReferenceName);
			sourceReferenceObject.setSourceReferenceKey(sourceReferenceKey);
			sourceReferenceObject.setAdditionalInfo("{}");

			sourceReferenceObject = sourceReferenceObjectRepository.save(sourceReferenceObject);
			refObjectId = sourceReferenceObject.getId();
		}

		AuditObjectChangeTracker auditObjectChangeTracker = auditObjectChangeTrackerRepository
				.findByRefObjectIdAndEventTypeAndEventOccurence(refObjectId, eventType, eventOccurence);
		if (auditObjectChangeTracker == null) {

			auditObjectChangeTracker = new AuditObjectChangeTracker();
			auditObjectChangeTracker.setEventOccurence(eventOccurence);
			auditObjectChangeTracker.setEventType(eventType);
			auditObjectChangeTracker.setRefObjectId(refObjectId);
			auditObjectChangeTracker = auditObjectChangeTrackerRepository.save(auditObjectChangeTracker);
		}

		AuditAttributeChangeTracker auditAttributeChangeTracker = new AuditAttributeChangeTracker();
		auditAttributeChangeTracker.setAttributeName(attributeName);
		auditAttributeChangeTracker.setOldValue(oldValue);
		auditAttributeChangeTracker.setNewValue(newValue);
		auditAttributeChangeTracker.setChangedBy(changedBy);
		auditAttributeChangeTracker.setAuditObjectChangeTracker(auditObjectChangeTracker);
		// LocalDateTime currentDateTime = LocalDateTime.now();

		auditAttributeChangeTrackerRepository.save(auditAttributeChangeTracker);

	}
}