package com.acnovate.auditmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableJpaAuditing
public class AuditConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditConsumerApplication.class, args);
	}

}
