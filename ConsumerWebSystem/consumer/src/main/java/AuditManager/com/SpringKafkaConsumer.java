package AuditManager.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class SpringKafkaConsumer {

	public static void main(String[] args) {
		Properties properties = new Properties();

		properties.setProperty("server.port", "8081");
		properties.setProperty("spring.kafka.consumer.bootstrap-servers", "localhost:9092");
		properties.setProperty("spring.kafka.consumer.group-id", "Subject");
		properties.setProperty("spring.kafka.consumer.auto-offset-reset", "earliest");
		properties.setProperty("spring.kafka.consumer.key-deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.setProperty("spring.kafka.consumer.value-deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		SpringApplication.run(SpringKafkaConsumer.class, args);
	}

}

