package com.acnovate.kafka.consumer.AuditManager.domain.dto.event;

public class QiwkEvent {
	
	private QiwkEventMetadata metadata;
	private QiwkEventPayload payload;
	public QiwkEventMetadata getMetadata() {
		return metadata;
	}
	public void setMetadata(QiwkEventMetadata metadata) {
		this.metadata = metadata;
	}
	public QiwkEventPayload getPayload() {
		return payload;
	}
	public void setPayload(QiwkEventPayload payload) {
		this.payload = payload;
	}	

}
