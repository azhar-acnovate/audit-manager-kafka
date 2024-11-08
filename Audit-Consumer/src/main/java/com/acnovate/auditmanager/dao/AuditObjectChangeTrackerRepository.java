package com.acnovate.auditmanager.dao;

import com.acnovate.auditmanager.domain.entity.AuditObjectChangeTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AuditObjectChangeTrackerRepository extends JpaRepository<AuditObjectChangeTracker, Long> {

	AuditObjectChangeTracker findByRefObjectIdAndEventTypeAndEventOccurence(Long refObjectId, String eventType,
			Date eventOccurence);

}
