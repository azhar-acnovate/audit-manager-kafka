package com.acnovate.kafka.consumer.AuditManager.listener;


import com.acnovate.kafka.consumer.AuditManager.handler.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Sample Kafka listener. Listener to get messages from specific topic. Real
 * application could have more of these listeners.
 *
 * @author Shubham Angachekar
 */
@EnableKafka
@Profile(value = "!test")
@Slf4j
@Component
public class AuditEtlListener extends AbstractBaseKafkaListener {

    @Autowired
    public AuditEtlListener(Collection<MessageHandler> messageHandlers) {
        super(messageHandlers);
    }

    /**
     * Receiving message headers and specific message
     *
     * @param message message content
     * @param headers message header
     * @see AbstractBaseKafkaListener#onMessage(String, MessageHeaders,
     * Acknowledgment)
     */
    @Override
    // `idIsGroup = false` is important for
    // `<spring-kafka.version>2.5.3.RELEASE</spring-kafka.version>`
    // otherwise consumer group will be `kafka_consumerListener` ignoring
    // `spring.kafka.consumer.group-id` property
    @KafkaListener(id = "kafka_auditEtlListener", idIsGroup = false, topics = {"${kafka.topic.audit}"})
    // Acknowledgment is for manual commits used along with ErrorTemplate.
    // If you do not need such kind of error control, it can be removed along with
    // `spring.kafka.listener.ack-mode: MANUAL_IMMEDIATE`
    public void onMessage(String message, MessageHeaders headers, Acknowledgment ack) {
        super.onMessage(message, headers, ack);
    }


}
