package com.acnovate.auditmanager.dao;

import com.acnovate.auditmanager.domain.entity.AuditAttributeChangeTracker;
import com.acnovate.auditmanager.domain.entity.AuditObjectChangeTracker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditAttributeChangeTrackerRepository extends JpaRepository<AuditAttributeChangeTracker, Integer> {

	AuditAttributeChangeTracker findByAuditObjectChangeTrackerAndAttributeName(
			AuditObjectChangeTracker auditObjectChangeTracker, String attributeName);

	AuditAttributeChangeTracker findTop1ByAttributeNameAndAuditObjectChangeTrackerRefObjectIdOrderByAuditObjectChangeTrackerUpdatedAtDesc(
			String attributeName, Long refObjectId);
}
