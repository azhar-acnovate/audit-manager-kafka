package com.acnovate.kafka.consumer.AuditManager.dao;

import com.acnovate.kafka.consumer.AuditManager.domain.entity.AttributeChangeTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttributeChangeTrackerRepository extends JpaRepository<AttributeChangeTracker, Integer> {
//    Optional<AttributeChangeTracker> findByAttributechangetracker(String AttributeName, String OldValue, String NewValue, String ChangedBy);
}


