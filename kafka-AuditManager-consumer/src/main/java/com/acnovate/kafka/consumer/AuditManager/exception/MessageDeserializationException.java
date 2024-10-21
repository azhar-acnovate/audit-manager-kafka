package com.acnovate.kafka.consumer.AuditManager.exception;

/**
 * Exception thrown when a Kafka message fails to deserialize.
 * This exception indicates a problem with converting the raw message data
 * into a meaningful format, often due to incorrect data format or schema mismatch.
 *
 * @author Shubham Angachekar
 */
public class MessageDeserializationException extends RuntimeException {

    public MessageDeserializationException(String message, Throwable cause) {
        super(message, cause);
    }

}
