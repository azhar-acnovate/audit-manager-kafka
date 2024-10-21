package AuditManager.kafka.producer;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import AuditManager.kafka.producer.dto.QiwkEventMetadata;
import AuditManager.kafka.producer.dto.QiwkEventPayload;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

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

	public QiwkEventMetadata setMetadata(String eventName) {
		QiwkEventMetadata metadata = new QiwkEventMetadata();
		metadata.setEventName(eventName);
		metadata.setEventSource("QIWK_ETL");
		metadata.setEventVersion("1.0");
		metadata.setEventTime(LocalDateTime.now().toString());
		return metadata;
	}

	public QiwkEventPayload setPayload(String mode, String objectName, HashMap<String, String> dataMap) {
		QiwkEventPayload payload = new QiwkEventPayload();
		payload.setObjectName(objectName);
		payload.setMode(mode);
		payload.setDataMap(dataMap);
		return payload;
	}

}
