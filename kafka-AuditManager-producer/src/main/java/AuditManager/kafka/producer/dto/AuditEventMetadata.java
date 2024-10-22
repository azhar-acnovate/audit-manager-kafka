package AuditManager.kafka.producer.dto;

public class AuditEventMetadata {	
	
	private String eventVersion;
	private String eventName;
	private String eventSource;
	private String eventTime;
	public String getEventVersion() {
		return eventVersion;
	}
	public void setEventVersion(String eventVersion) {
		this.eventVersion = eventVersion;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventSource() {
		return eventSource;
	}
	public void setEventSource(String eventSource) {
		this.eventSource = eventSource;
	}
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	
	

}
