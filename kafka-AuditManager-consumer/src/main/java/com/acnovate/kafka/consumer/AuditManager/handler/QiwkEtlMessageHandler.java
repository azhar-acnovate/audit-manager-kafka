package com.acnovate.kafka.consumer.AuditManager.handler;

import com.acnovate.kafka.consumer.AuditManager.domain.dto.event.QiwkEvent;

import java.util.Map;

public class QiwkEtlMessageHandler implements MessageHandler{

    @Override
    public String handle(QiwkEvent message, Map<String, Object> headers) {
        return null;
    }

    @Override
    public boolean isSupported(String eventName) {
        return false;
    }
}
