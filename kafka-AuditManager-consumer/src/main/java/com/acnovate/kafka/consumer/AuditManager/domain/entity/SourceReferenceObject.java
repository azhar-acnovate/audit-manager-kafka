package com.acnovate.kafka.consumer.AuditManager.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "source_reference_object")
public class SourceReferenceObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_reference_name", nullable = true)
    private String sourceReferenceName;

    @Column(name = "source_reference_key", nullable = true)
    private String sourceReferenceKey;

    @Column(name = "additional_info", nullable = true)
    private String additionalInfo;  // If using a JSON library, you may want to parse this to an object

    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Column(name = "created_by",nullable = true)
    private Long createdBy;

    @Column(name = "updated_by",nullable = true)
    private Long updatedBy;

    @Column(name = "active", nullable = true)
    private Long active;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getActive() {
        return active;
    }

    public void setActive(Long active) {
        this.active = active;
    }
}
