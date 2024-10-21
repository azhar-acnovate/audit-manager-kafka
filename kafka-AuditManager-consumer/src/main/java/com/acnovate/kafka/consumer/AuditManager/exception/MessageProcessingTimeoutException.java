package com.acnovate.kafka.consumer.AuditManager.exception;

/**
 * Exception thrown when the processing time for a message exceeds the time
 * allowed by Kafka to operate on a given batch. If this time limit is surpassed,
 * the consumer loses ownership of the partition, and any commit action will be rejected.
 */
public class MessageProcessingTimeoutException extends RuntimeException {

    public MessageProcessingTimeoutException(String message) {
        super(message);
    }

    public MessageProcessingTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

}

