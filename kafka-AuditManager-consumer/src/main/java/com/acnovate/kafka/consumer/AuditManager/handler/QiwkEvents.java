package com.acnovate.kafka.consumer.AuditManager.handler;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public enum QiwkEvents {

    POST_CREATE_PERSIST("POST_CREATE_PERSIST"),
    POST_UPDATE_PERSIST("POST_UPDATE_PERSIST"),
    POST_DELETE("POST_DELETE");

    private final String eventName;

    QiwkEvents(final String eventName) {
        this.eventName = eventName;
    }

    public static boolean contains(final String name) {
        return Stream.of(QiwkEvents.values()).anyMatch(value -> value.eventName.equals(name));
    }

    public static Optional<QiwkEvents> getByString(String s) {
        return Arrays.stream(QiwkEvents.values()).filter(val -> val.name().equalsIgnoreCase(s)).findAny();
    }
}
