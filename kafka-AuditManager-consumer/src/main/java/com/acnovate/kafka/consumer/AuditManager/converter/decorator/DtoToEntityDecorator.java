package com.acnovate.kafka.consumer.AuditManager.converter.decorator;

import java.util.function.BiConsumer;

public interface DtoToEntityDecorator<T, E> extends BiConsumer<T, E> {
}
