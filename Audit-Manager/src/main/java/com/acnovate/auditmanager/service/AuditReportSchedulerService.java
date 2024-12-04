package com.acnovate.auditmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuditReportSchedulerService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AuditReportService auditReportService;

	private static final String AUDIT_REPORT_URL = "http://localhost:8081/event/auditreport";

//	@Scheduled(cron = "0 42 17 * * ?")
//	public void hitAuditReportUrl() {
//		try {
//			String response = restTemplate.postForObject(AUDIT_REPORT_URL, null, String.class);
//			System.out.println("Response from audit report service: " + response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	@Scheduled(fixedDelay = 1000 * 60 * 5) // Every 5 min
	public void bulkData() {
		for (int i = 0; i < 30; i++) {
			auditReportService.publishBulkData();
		}

	}
}
