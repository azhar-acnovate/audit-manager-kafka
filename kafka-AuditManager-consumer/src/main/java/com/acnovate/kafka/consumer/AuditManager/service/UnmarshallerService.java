package com.acnovate.kafka.consumer.AuditManager.service;

import com.acnovate.kafka.consumer.AuditManager.exception.MessageDeserializationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Unmarshall messages from Kafka topics into classes or Parametrized classes
 *
 * @author Shubham Angachekar
 */
@Slf4j
@Service
public class UnmarshallerService {

    private final ObjectMapper objectMapper;

    @Autowired
    public UnmarshallerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * @param str   plain text message to be deserialized
     * @param clazz target deserialization result class
     * @param <T>   type of target deserialization class
     * @return instance of class {@code T} deserialized from {@code str} message
     * @throws MessageDeserializationException in case of any deserialization issue
     */
    public <T> T unmarshal(String str, Class<T> clazz) {
        try {
            return objectMapper.readValue(str, clazz);
        } catch (IOException e) {
            log.error("Failed to deserialize incoming message: {}", str);
            throw new MessageDeserializationException("Failed to deserialize incoming message", e);
        }
    }

    /**
     * @param str     plain text message to be deserialized
     * @param typeRef target deserialization result class type reference
     * @param <T>     type of target deserialization class
     * @return instance of class {@code T} deserialized from {@code str} message
     * @throws MessageDeserializationException in case of any deserialization issue
     */
    public <T> T unmarshal(String str, TypeReference<T> typeRef) {
        try {
            return objectMapper.readValue(str, typeRef);
        } catch (IOException e) {
            log.error("Failed to deserialize incoming message: {}", str);
            throw new MessageDeserializationException("Failed to deserialize incoming message", e);
        }
    }

    /**
     * @param obj plain text message to be deserialized
     * @param <T> type of target deserialization class
     * @return instance of class {@code T} deserialized from {@code str} message
     * @throws MessageDeserializationException in case of any deserialization issue
     */
    public <T> T unmarshal(Object obj, TypeReference<T> typeReference) {
        return objectMapper.convertValue(obj, typeReference);
    }

    public <T> T unmarshal(Object object, Class<T> clazz) {
        return objectMapper.convertValue(object, clazz);
    }
}

