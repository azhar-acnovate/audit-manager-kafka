package com.acnovate.auditmanager.controller;

import com.acnovate.auditmanager.dto.AuditRequest;
import com.acnovate.auditmanager.dto.AuditResponse;
import com.acnovate.auditmanager.service.AuditReportService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/event")
public class AuditReportController {

	public static final Logger log = LoggerFactory.getLogger(AuditReportController.class);

	@Autowired
	private final AuditReportService auditReportService;

	// You can directly call this method without the URL
	@PostMapping(value = "/auditreport")
	public ResponseEntity<?> send() {
		try {
			// Directly calling the service method
			auditReportService.convertAndPublishAuditReport();
			return new ResponseEntity<>("Success", HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error during processing audit report", e);
			return new ResponseEntity<>("Failure", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
