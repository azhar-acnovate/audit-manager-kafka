package com.acnovate.kafka.consumer.AuditManager.dao;

import com.acnovate.kafka.consumer.AuditManager.domain.entity.SourceReferenceObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SourceReferenceObjectRepository extends JpaRepository<SourceReferenceObject, Long> {

    Optional<SourceReferenceObject> findBySourceReferenceNameAndSourceReferenceKey(String sourceReferenceName, String sourceReferenceKey);


}
