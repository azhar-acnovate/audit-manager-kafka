package com.acnovate.kafka.consumer.AuditManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class KafkaQiwkEtlConsumerApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(KafkaQiwkEtlConsumerApplication.class, args);


	}

}
