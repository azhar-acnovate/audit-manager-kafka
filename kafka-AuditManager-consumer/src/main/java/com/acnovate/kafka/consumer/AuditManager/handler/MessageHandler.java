package com.acnovate.kafka.consumer.AuditManager.handler;


import com.acnovate.kafka.consumer.AuditManager.domain.dto.event.QiwkEvent;

import java.util.Map;

/**
 * All Kafka message handlers must implement this interface
 *
 * @author Shubham Angachekar
 */
public interface MessageHandler {

    /**
     * Implements a business logic for handling incoming Kafka message
     *
     * @param message KafkaMessage unmarshalled as generic SimpleApplicationEvent
     * @param headers the headers Kafka headers
     * @return message processing result. Cannot be {@code null}
     */
    String handle(QiwkEvent message, Map<String, Object> headers);

    /**
     * Tells if particular handler is supposed to react on incoming event
     *
     * @param eventName the event name
     * @return true or false
     */
    boolean isSupported(String eventName);

}

