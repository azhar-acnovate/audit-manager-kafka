package AuditManager.kafka.producer.controller;



import lombok.RequiredArgsConstructor;
import AuditManager.kafka.producer.KafkaProducer;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/event")
public class KafkaController {
//
//	public static final Logger log = LoggerFactory.getLogger(KafkaController.class);
//
//	@Autowired
//	private KafkaProducer kafkaProducer;
//
//	@Value(value="${kafka.producer.topic.name}")
//	private String kafkaProducerTopic;
//
//	Gson gson = new Gson();
//
//
//    @PostMapping(value = "/create")
//    public ResponseEntity<?> send(@RequestBody String event){
//    	log.info("kafka producer message - "+event);
//		kafkaProducer.send(kafkaProducerTopic,10,UUID.randomUUID().toString(),event);
//		return new ResponseEntity("Success", HttpStatus.OK);
//    }
//
//    @PostMapping(value = "/delete")
//    public ResponseEntity<?> sendDeleteEvent(@RequestBody String event){
//    	log.info("kafka producer message - "+event);
//    	System.out.println("kafka producer message - "+event);
//		kafkaProducer.send(kafkaProducerTopic,10,UUID.randomUUID().toString(),event);
//		return new ResponseEntity("Success", HttpStatus.OK);
//    }
}