package com.acnovate.kafka.consumer.AuditManager.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "audit_object_change_tracker")
public class AuditObjectChangeTracker extends AuditEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long refObjectId;
	private String eventType;
	private Date eventOccurence;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Date getEventOccurence() {
		return eventOccurence;
	}

	public void setEventOccurence(Date eventOccurence) {
		this.eventOccurence = eventOccurence;
	}

	public Long getRefObjectId() {
		return refObjectId;
	}

	public void setRefObjectId(Long refObjectId) {
		this.refObjectId = refObjectId;
	}

}
