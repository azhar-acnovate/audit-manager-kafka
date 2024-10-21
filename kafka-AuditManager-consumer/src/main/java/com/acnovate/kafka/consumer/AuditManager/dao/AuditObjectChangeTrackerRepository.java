package com.acnovate.kafka.consumer.AuditManager.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acnovate.kafka.consumer.AuditManager.domain.entity.AuditObjectChangeTracker;

@Repository
public interface AuditObjectChangeTrackerRepository extends JpaRepository<AuditObjectChangeTracker, Long> {

	AuditObjectChangeTracker findByRefObjectIdAndEventTypeAndEventOccurence(Long refObjectId, String eventType,
			Date eventOccurence);

}
