package com.acnovate.auditmanager.dao;

import com.acnovate.auditmanager.domain.entity.SourceReferenceObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SourceReferenceObjectRepository extends JpaRepository<SourceReferenceObject, Long> {

    Optional<SourceReferenceObject> findBySourceReferenceNameAndSourceReferenceKey(String sourceReferenceName, String sourceReferenceKey);


}
