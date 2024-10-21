package AuditManager.kafka.producer.dto;

import java.io.Serializable;

public class ProducerMetadata implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4201887463985694528L;
	private String eventVersion;
	private String eventSource;
	private String eventName;
	private String eventTime;
	
	
	public ProducerMetadata() {
		super();
		
	}


	public ProducerMetadata(String eventVersion, String eventSource,
			String eventName, String eventTime) {
		super();
		this.eventVersion = eventVersion;
		this.eventSource = eventSource;
		this.eventName = eventName;
		this.eventTime = eventTime;
	}


	public String getEventVersion() {
		return eventVersion;
	}


	public void setEventVersion(String eventVersion) {
		this.eventVersion = eventVersion;
	}


	public String getEventSource() {
		return eventSource;
	}


	public void setEventSource(String eventSource) {
		this.eventSource = eventSource;
	}


	public String getEventName() {
		return eventName;
	}


	public void setEventName(String eventName) {
		this.eventName = eventName;
	}


	public String getEventTime() {
		return eventTime;
	}


	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((eventName == null) ? 0 : eventName.hashCode());
		result = prime * result
				+ ((eventSource == null) ? 0 : eventSource.hashCode());
		result = prime * result
				+ ((eventTime == null) ? 0 : eventTime.hashCode());
		result = prime * result
				+ ((eventVersion == null) ? 0 : eventVersion.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProducerMetadata other = (ProducerMetadata) obj;
		if (eventName == null) {
			if (other.eventName != null)
				return false;
		} else if (!eventName.equals(other.eventName))
			return false;
		if (eventSource == null) {
			if (other.eventSource != null)
				return false;
		} else if (!eventSource.equals(other.eventSource))
			return false;
		if (eventTime == null) {
			if (other.eventTime != null)
				return false;
		} else if (!eventTime.equals(other.eventTime))
			return false;
		if (eventVersion == null) {
			if (other.eventVersion != null)
				return false;
		} else if (!eventVersion.equals(other.eventVersion))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "ProducerMetadata [eventVersion=" + eventVersion
				+ ", eventSource=" + eventSource + ", eventName=" + eventName
				+ ", eventTime=" + eventTime + "]";
	}
	
	
	

}
