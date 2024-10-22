package AuditManager.kafka.producer.dto;

public class AuditEvent {
	
	private AuditEventMetadata metadata;
	private AuditEventPayload payload;
	public AuditEventMetadata getMetadata() {
		return metadata;
	}
	public void setMetadata(AuditEventMetadata metadata) {
		this.metadata = metadata;
	}
	public AuditEventPayload getPayload() {
		return payload;
	}
	public void setPayload(AuditEventPayload payload) {
		this.payload = payload;
	}	

}