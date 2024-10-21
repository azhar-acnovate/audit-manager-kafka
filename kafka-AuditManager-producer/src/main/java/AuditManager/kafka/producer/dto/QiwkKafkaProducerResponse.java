package AuditManager.kafka.producer.dto;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
public class QiwkKafkaProducerResponse {
	
	private String status;
	private String message;
	@JsonIgnoreProperties
	private HttpStatus httpstatus;
}
