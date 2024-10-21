package com.acnovate.kafka.consumer.AuditManager.service;

public interface QiwkPersistenceService<T> {

    void save(T object);

    void deleteById(String objectName, int id);
}
