package com.acnovate.auditmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@SpringBootApplication
@EnableKafka
public class AuditManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditManagerApplication.class, args);
	}

	// Configure RestTemplate bean for HTTP requests
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
