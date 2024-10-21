package com.acnovate.kafka.consumer.AuditManager.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "audit_attribute_change_tracker")
public class AuditAttributeChangeTracker extends AuditEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "attribute_name", nullable = true)
	private String attributeName;

	@Column(name = "old_value", nullable = true)
	private String oldValue;

	@Column(name = "new_value", nullable = true)
	private String newValue;

	@Column(name = "changed_by", nullable = true)
	private String changedBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "audit_object_change_tracker_id")
	private AuditObjectChangeTracker auditObjectChangeTracker;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public AuditObjectChangeTracker getAuditObjectChangeTracker() {
		return auditObjectChangeTracker;
	}

	public void setAuditObjectChangeTracker(AuditObjectChangeTracker auditObjectChangeTracker) {
		this.auditObjectChangeTracker = auditObjectChangeTracker;
	}

}
