package com.acnovate.kafka.consumer.AuditManager.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "AUDIT_OBJECT_CHANGE_TRACKER")
public class QiwkAuditReport implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private long REF_OBJECT_ID;
    private String EVENT_TYPE;
    private String EVENT_OCCURENCE;
    private String CREATED_AT;
    private String UPDATED_AT;
    private long CREATED_BY;
    private long UPDATED_BY;
    private long ACTIVE;
}
