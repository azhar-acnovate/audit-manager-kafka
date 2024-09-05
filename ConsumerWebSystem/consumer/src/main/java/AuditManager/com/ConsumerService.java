package AuditManager.com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConsumerService {

    Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    List<String> PartsList = new LinkedList<>();
    List<String> DocumentList = new LinkedList<>();
    List<String> CRList = new LinkedList<>();
    List<String> CNList = new LinkedList<>();

    @KafkaListener(topics = "Parts", groupId = "Subject")
    public void getPhysicsMessages(String message) {
        logger.info("Parts: "+message);
        PartsList.add(0, message);
    }

    @KafkaListener(topics = "Documents", groupId = "Subject")
    public void getChemistryMessages(String message) {
        logger.info("Documents: "+message);
        DocumentList.add(0, message);
    }

    @KafkaListener(topics = "ChangeRequest", groupId = "Subject")
    public void getBiologyMessages(String message) {
        logger.info("ChangeRequest: "+message);
        CRList.add(0, message);
    }

    @KafkaListener(topics = "ChangeNotice", groupId = "Subject")
    public void getMathematicsMessage(String message) {
        logger.info("ChangeNotice: "+message);
        CNList.add(0, message);
    }



}
