package AuditManager.kafka.producer.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
@Component
public class AuditResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private String jobId;

	public String getJobId() {
		return this.jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> hashMap = new LinkedHashMap<>();

		hashMap.put("jobId", this.jobId);
		return hashMap;
	}
}
