package com.acnovate.auditmanager;


import com.acnovate.auditmanager.dto.AuditEventMetadata;
import com.acnovate.auditmanager.dto.AuditEventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void send(String topicName,String key,String message){
		kafkaTemplate.send(topicName,key,message);
	}

	public void send(String topicName,Integer partition,String key,String message){
		kafkaTemplate.send(topicName,partition,key,message);
	}

	public AuditEventMetadata setMetadata(String eventName) {
		AuditEventMetadata metadata = new AuditEventMetadata();
		metadata.setEventName(eventName);
		metadata.setEventSource("AUDIT_REPORT");
		metadata.setEventVersion("1.0");
		metadata.setEventTime(LocalDateTime.now().toString());
		return metadata;
	}

	public AuditEventPayload setPayload( String objectName, HashMap<String, String> dataMap) {
		AuditEventPayload payload = new AuditEventPayload();
		payload.setObjectName(objectName);
		payload.setDataMap(dataMap);
		return payload;
	}

}
