package AuditManager.com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class ProducerController {

    Logger logger = LoggerFactory.getLogger(ProducerController.class);

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/publish")
    public void writeMessageToTopic(@RequestParam("subject") String subject, @RequestParam("message") String message) {
        logger.info("Subject: "+subject+" | Message: "+message);
        kafkaTemplate.send(subject, message);
    }

}
