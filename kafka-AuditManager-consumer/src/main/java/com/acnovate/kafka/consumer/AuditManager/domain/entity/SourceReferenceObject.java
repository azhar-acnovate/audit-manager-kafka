package com.acnovate.kafka.consumer.AuditManager.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "source_reference_object")
public class SourceReferenceObject extends AuditEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "source_reference_name", nullable = true)
	private String sourceReferenceName;

	@Column(name = "source_reference_key", nullable = true)
	private String sourceReferenceKey;

	@Column(name = "additional_info", nullable = true)
	private String additionalInfo; // If using a JSON library, you may want to parse this to an object

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSourceReferenceName() {
		return sourceReferenceName;
	}

	public void setSourceReferenceName(String sourceReferenceName) {
		this.sourceReferenceName = sourceReferenceName;
	}

	public String getSourceReferenceKey() {
		return sourceReferenceKey;
	}

	public void setSourceReferenceKey(String sourceReferenceKey) {
		this.sourceReferenceKey = sourceReferenceKey;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

}
