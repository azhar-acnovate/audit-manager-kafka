package com.acnovate.kafka.consumer.AuditManager.exception;

public class UnsupportedEventException extends RuntimeException {

    public UnsupportedEventException(String message) {
        super(message);
    }
}
