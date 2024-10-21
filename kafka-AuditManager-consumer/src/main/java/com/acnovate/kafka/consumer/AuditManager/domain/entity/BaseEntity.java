package com.acnovate.kafka.consumer.AuditManager.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@MappedSuperclass
public class BaseEntity implements Serializable {

    @CreatedDate
    @Column(name = "RECORD_CREATION_DATE", nullable = false)
    private LocalDateTime recordCreationDate;

    public LocalDateTime getRecordCreationDate() {
        return recordCreationDate;
    }

    @PrePersist
    public void setRecordCreationDate() {
        this.recordCreationDate = LocalDateTime.now(ZoneId.of("CET"));
    }

}
