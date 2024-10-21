package com.acnovate.kafka.consumer.AuditManager.dao;

import com.acnovate.kafka.consumer.AuditManager.domain.entity.QiwkAuditReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QiwkAuditReportRepository extends JpaRepository<QiwkAuditReport, Integer> {
    @Query("FROM QiwkAuditReport S WHERE S.REF_OBJECT_ID = :REF_OBJECT_ID")
    Optional<QiwkAuditReport> findByREF_OBJECT_ID(Long REF_OBJECT_ID);

}
