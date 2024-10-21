package AuditManager.kafka.producer.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.google.gson.Gson;

import AuditManager.kafka.producer.KafkaProducer;
import AuditManager.kafka.producer.dto.QiwkEvent;
import AuditManager.kafka.producer.dto.QiwkEventMetadata;
import AuditManager.kafka.producer.dto.QiwkEventPayload;
import AuditManager.kafka.producer.dto.QiwkResponse;

@Service
public class AuditReportService {

	@Autowired
	private KafkaProducer kafkaProducer;

	@Value(value="${kafka.producer.topic.auditreport.update}")
	private String kafkaProducerTopicAuditReportUpdate;
	@Value(value="${kafka.producer.topic.auditreport.update.partitions}")
	private Integer auditreportUpdatePartitions;

	private Gson gson = new Gson();

	public static String objectName = "AUDIT_OBJECT_CHANGE_TRACKER";
	public static final Logger log = LoggerFactory.getLogger(AuditReportService.class);

	public void convertAndPublishAuditReport(String mode,QiwkResponse qiwkServiceResponse) {
		ArrayList<HashMap<String, String>> rootElement = FileReader.readExcelFile("C:\\Users\\ACNusr2\\Documents\\SampleData\\CadData_Sample_Data_audit_object_change_tracker_Test_updated1.xlsx");
		log.info("AuditReportService - convertAndPublishAuditReport - start");
		final StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		for(HashMap<String, String> map : rootElement) {
			String branchId = (String) map.get("REF_OBJECT_ID");
			String key = branchId+objectName;
			Integer hashCode = key.hashCode() & Integer.MAX_VALUE;
			log.info("Partition value :"+hashCode%10);
			QiwkEvent qiwkEvent = new QiwkEvent();
			QiwkEventMetadata metadata = kafkaProducer.setMetadata(objectName.toUpperCase());
			QiwkEventPayload payload = kafkaProducer.setPayload(mode, objectName, map);
			qiwkEvent.setMetadata(metadata);
			qiwkEvent.setPayload(payload);
			String eventPayload = gson.toJson(qiwkEvent);
			log.info("Value of PayLoad : "+eventPayload);
				kafkaProducer.send(kafkaProducerTopicAuditReportUpdate,hashCode%auditreportUpdatePartitions,key,eventPayload);
		}
		stopWatch.stop();
		log.info("Total time to process "+rootElement.size()+" events is "+stopWatch.getTotalTimeMillis()+" ms");
		log.info("AuditReportService - convertAndPublishAuditReport - end");
	}
}
