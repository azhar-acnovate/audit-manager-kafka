package com.acnovate.auditmanager.dao;


import com.acnovate.auditmanager.domain.entity.AuditAttributeChangeTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditAttributeChangeTrackerRepository extends JpaRepository<AuditAttributeChangeTracker, Integer> {
}
