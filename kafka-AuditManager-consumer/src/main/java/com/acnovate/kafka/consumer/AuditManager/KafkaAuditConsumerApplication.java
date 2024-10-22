package com.acnovate.kafka.consumer.AuditManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableJpaAuditing
public class KafkaAuditConsumerApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(KafkaAuditConsumerApplication.class, args);

	}

}
