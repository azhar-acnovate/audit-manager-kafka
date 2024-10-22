package AuditManager.kafka.producer.controller;

import java.util.HashMap;

import jakarta.ws.rs.core.Response;
//import jakarta.ws.rs.core.Response;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
//import AuditManager.kafka.producer.dto.AuditKafkaProducerResponse;
import AuditManager.kafka.producer.dto.AuditRequest;
import AuditManager.kafka.producer.dto.AuditResponse;
import AuditManager.kafka.producer.service.AuditReportService;
//import AuditManager.kafka.producer.service.AuditRestClient;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/event")
public class AuditReportController {

	public static final Logger log = LoggerFactory.getLogger(AuditReportController.class);

	HashMap<String,String> queryParam = new HashMap<>();
	@Autowired
	private AuditReportService auditreportService;

	AuditResponse auditServiceResponse;
	AuditRequest request;

	Gson gson = new Gson();

    @PostMapping(value = "/auditreport/{mode}")
    public ResponseEntity<?> send(@RequestBody String event, @PathVariable String mode) throws JSONException {
    	log.info("kafka producer message - "+event);
    	auditServiceResponse = gson.fromJson(event, AuditResponse.class);
		auditreportService.convertAndPublishAuditReport(mode,auditServiceResponse);
		return new ResponseEntity("Success", HttpStatus.OK);
    }
}