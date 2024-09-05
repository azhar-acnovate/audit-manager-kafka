package AuditManager.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class SpringKafkaProducer {

	public static void main(String[] args) {

		Properties properties = new Properties();
		properties.setProperty("server.port", "8080");
		properties.setProperty("spring.kafka.producer.bootstrap-servers", "localhost:9092");
		properties.setProperty("spring.kafka.producer.key-serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.setProperty("spring.kafka.producer.value-serializer", "org.apache.kafka.common.serialization.StringSerializer");

		SpringApplication.run(SpringKafkaProducer.class, args);
	}
}