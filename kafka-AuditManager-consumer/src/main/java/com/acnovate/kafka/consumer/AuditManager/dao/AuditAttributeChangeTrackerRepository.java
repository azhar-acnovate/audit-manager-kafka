package com.acnovate.kafka.consumer.AuditManager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acnovate.kafka.consumer.AuditManager.domain.entity.AuditAttributeChangeTracker;

@Repository
public interface AuditAttributeChangeTrackerRepository extends JpaRepository<AuditAttributeChangeTracker, Integer> {
}
