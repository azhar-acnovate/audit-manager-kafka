package com.acnovate.kafka.consumer.AuditManager.handler;

import com.acnovate.kafka.consumer.AuditManager.dao.AttributeChangeTrackerRepository;
import com.acnovate.kafka.consumer.AuditManager.dao.QiwkAuditReportRepository;
import com.acnovate.kafka.consumer.AuditManager.dao.SourceReferenceObjectRepository;
import com.acnovate.kafka.consumer.AuditManager.domain.dto.event.QiwkEvent;
import com.acnovate.kafka.consumer.AuditManager.domain.dto.event.QiwkEventPayload;
import com.acnovate.kafka.consumer.AuditManager.domain.entity.AttributeChangeTracker;
import com.acnovate.kafka.consumer.AuditManager.domain.entity.QiwkAuditReport;
import com.acnovate.kafka.consumer.AuditManager.domain.entity.SourceReferenceObject;
import com.acnovate.kafka.consumer.AuditManager.util.XMLValueUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
@Component
@Slf4j
@RequiredArgsConstructor
public class QiwkAuditReportHandler implements MessageHandler {
    private static final Logger logger = LoggerFactory.getLogger(QiwkAuditReportHandler.class);
    private static final StopWatch stopWatch = new StopWatch();
    public static boolean TRANSACTION_ROLLBACK = false;
    private static HashMap<String, String> columnMap = new HashMap<String, String>();
    @Autowired
    private QiwkAuditReportRepository qiwkAuditReportRepository;
    @Autowired
    private SourceReferenceObjectRepository sourceReferenceObjectRepository;
    @Autowired
    private AttributeChangeTrackerRepository attributeChangetrackerRepository;

    @Override
    public String handle(QiwkEvent event, Map<String, Object> headers) {
        try {
            QiwkEventPayload payload = event.getPayload();
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
        logger.info("Value of map : "+map);
        logger.info("Size of map : "+map.size());
        logger.info("Mode: "+mode);
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
        String AttributeName = map.get("attribute_name");
        String OldValue = map.get("old_value");
        String NewValue = map.get("new_value");
        String ChangedBy = map.get("changed_by");

        Optional<SourceReferenceObject> optionalSourceReferenceObject = sourceReferenceObjectRepository
                .findBySourceReferenceNameAndSourceReferenceKey(sourceReferenceName, sourceReferenceKey);

        if (optionalSourceReferenceObject.isPresent()) {
            SourceReferenceObject sourceReferenceObject = optionalSourceReferenceObject.get();
            refObjectId = sourceReferenceObject.getId();
        } else {
            SourceReferenceObject sourceReferenceObject = new SourceReferenceObject();
            sourceReferenceObject.setSourceReferenceName(sourceReferenceName);
            sourceReferenceObject.setSourceReferenceKey(sourceReferenceKey);
            sourceReferenceObject.setAdditionalInfo("Additional");
            LocalDateTime currentDateTime = LocalDateTime.now();
            sourceReferenceObject.setCreatedAt(currentDateTime);
            sourceReferenceObject.setUpdatedAt(currentDateTime);
            sourceReferenceObject.setCreatedBy(Long.valueOf(map.getOrDefault("created_by", "0")));
            sourceReferenceObject.setUpdatedBy(Long.valueOf(map.getOrDefault("updated_by", "0")));
            sourceReferenceObject.setActive(Long.valueOf(map.getOrDefault("active", "0")));

            sourceReferenceObject = sourceReferenceObjectRepository.save(sourceReferenceObject);
            refObjectId = sourceReferenceObject.getId();
        }

        logger.info("Value of map in FR method : "+map);

        AttributeChangeTracker Changetracker = new AttributeChangeTracker();
        Changetracker.setAttributeName(AttributeName);
        Changetracker.setOldValue(OldValue);
        Changetracker.setNewValue(NewValue);
        Changetracker.setChangedBy(ChangedBy);
        LocalDateTime currentDateTime = LocalDateTime.now();
        Changetracker.setCreatedAt(currentDateTime);
        Changetracker.setUpdatedAt(currentDateTime);
        Changetracker.setCreatedBy(Long.valueOf(map.getOrDefault("created_by", "0")));
        Changetracker.setUpdatedBy(Long.valueOf(map.getOrDefault("updated_by", "0")));
        Changetracker.setActive(Long.valueOf(map.getOrDefault("active", "0")));

        attributeChangetrackerRepository.save(Changetracker);



//        Optional<AttributeChangeTracker> optionalAttributeChangeTrackerObject = attributeChangetrackerRepository
//                .findByAttributechangetracker(AttributeName,OldValue,NewValue,ChangedBy);

//        if () {
//            map.get(AttributeName);
//            AttributeChangeTracker AttributeTrackObject = optionalAttributeChangeTrackerObject.get();
//            AttributeName = String.valueOf(AttributeTrackObject.getAttributeName());
//            OldValue = String.valueOf(AttributeTrackObject.getOldValue());
//            NewValue = String.valueOf(AttributeTrackObject.getNewValue());
//            ChangedBy = String.valueOf(AttributeTrackObject.getChangedBy());
//        } else {
//            AttributeChangeTracker AttributeValue = new AttributeChangeTracker();
//            AttributeValue.setAttributeName(AttributeName);
//            AttributeValue.setOldValue(OldValue);
//            AttributeValue.setNewValue(NewValue);
//            AttributeValue.setChangedBy(ChangedBy);
//            LocalDateTime currentDateTime = LocalDateTime.now();
//            AttributeValue.setCreatedAt(currentDateTime);
//            AttributeValue.setUpdatedAt(currentDateTime);
//            AttributeValue.setCreatedBy(Long.valueOf(map.getOrDefault("created_by", "0")));
//            AttributeValue.setUpdatedBy(Long.valueOf(map.getOrDefault("updated_by", "0")));
//            AttributeValue.setActive(Long.valueOf(map.getOrDefault("active", "0")));
//
//            AttributeValue = attributeChangetrackerRepository.save(AttributeValue);
//            AttributeName = AttributeValue.getAttributeName();
//        }

        // Check if an existing audit report already exists for this refObjectId
        Optional<QiwkAuditReport> existingReport = qiwkAuditReportRepository.findByREF_OBJECT_ID(refObjectId);

        if (existingReport.isPresent()) {
            // Update the existing audit report
            updateAuditReport(existingReport.get(), map);
        } else {
            // Create a new audit report since it doesn't exist
            QiwkAuditReport ltdAuditReport = new QiwkAuditReport();
            setAuditReportFields(ltdAuditReport, map, refObjectId);

            qiwkAuditReportRepository.save(ltdAuditReport);
            logger.info("AuditReport saved successfully: " + ltdAuditReport.toString());
        }
    }

    private void updateAuditReport(QiwkAuditReport existingReport, HashMap<String, String> map) {
        try {
            existingReport.setEVENT_TYPE(map.getOrDefault("event_type", existingReport.getEVENT_TYPE()));
            existingReport.setEVENT_OCCURENCE(map.getOrDefault("event_occurence", existingReport.getEVENT_OCCURENCE()));
            existingReport.setCREATED_AT(map.getOrDefault("created_at", existingReport.getCREATED_AT()));
            existingReport.setUPDATED_AT(map.getOrDefault("updated_at", existingReport.getUPDATED_AT()));
            existingReport.setCREATED_BY(Long.parseLong(map.getOrDefault("created_by", String.valueOf(existingReport.getCREATED_BY()))));
            existingReport.setUPDATED_BY(Long.parseLong(map.getOrDefault("updated_by", String.valueOf(existingReport.getUPDATED_BY()))));
            existingReport.setACTIVE(Long.parseLong(map.getOrDefault("active", String.valueOf(existingReport.getACTIVE()))));

            // Set additional properties from the map
            setProperty(map, existingReport);

            qiwkAuditReportRepository.save(existingReport);
            logger.info("AuditReport updated successfully: " + existingReport.toString());
        } catch (Exception e) {
            logger.error("Error while updating AuditReport: ", e);
        }
    }

    private void setAuditReportFields(QiwkAuditReport auditReport, HashMap<String, String> map, Long refObjectId) {
        auditReport.setREF_OBJECT_ID(refObjectId);
        auditReport.setEVENT_TYPE(map.get("event_type"));
        auditReport.setCREATED_BY(Long.parseLong(map.getOrDefault("created_by", "0")));
        auditReport.setACTIVE(Long.parseLong(map.getOrDefault("active", "0")));
        auditReport.setUPDATED_BY(Long.parseLong(map.getOrDefault("updated_by", "0")));
        auditReport.setEVENT_OCCURENCE(map.get("event_occurence"));
        auditReport.setCREATED_AT(map.get("created_at"));
        auditReport.setUPDATED_AT(map.get("updated_at"));

        // Set additional properties from the map
        setProperty(map, auditReport);
    }
    public static QiwkAuditReport setProperty(HashMap<String, String> map,  QiwkAuditReport ltdAuditReport) {
        for (Field field : QiwkAuditReport.class.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String value = map.get(fieldName);
            if (value != null) {
                try {
                    if (field.getType() == String.class) {
                        field.set(ltdAuditReport, value);
                    } else if (field.getType() == long.class || field.getType() == Long.class) {
                        field.set(ltdAuditReport, Long.parseLong(value));
                    } else if (field.getType() == Date.class) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                        field.set(ltdAuditReport, sdf.parse(value));
                    } else if (field.getType() == Timestamp.class) {
                        field.set(ltdAuditReport, Timestamp.valueOf(value));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ltdAuditReport;
    }
}