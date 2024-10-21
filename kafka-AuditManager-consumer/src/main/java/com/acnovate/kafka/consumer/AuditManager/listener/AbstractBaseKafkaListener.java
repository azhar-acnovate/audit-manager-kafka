package com.acnovate.kafka.consumer.AuditManager.listener;


import com.acnovate.kafka.consumer.AuditManager.domain.dto.event.QiwkEvent;
import com.acnovate.kafka.consumer.AuditManager.errortemplate.ErrorTemplate;
import com.acnovate.kafka.consumer.AuditManager.exception.MaybeRecoverableException;
import com.acnovate.kafka.consumer.AuditManager.exception.MessageDeserializationException;
import com.acnovate.kafka.consumer.AuditManager.exception.MessageProcessingTimeoutException;
import com.acnovate.kafka.consumer.AuditManager.exception.RecoverableException;
import com.acnovate.kafka.consumer.AuditManager.exception.UnsupportedEventException;
import com.acnovate.kafka.consumer.AuditManager.handler.MessageHandler;
import com.acnovate.kafka.consumer.AuditManager.service.UnmarshallerService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.util.StopWatch;

import java.util.Collection;
import java.util.Collections;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Allows to consume messages from Kafka topic.
 *
 * @author Shubham Angachekar
 */
@Slf4j
public abstract class AbstractBaseKafkaListener extends KafkaSeekOffsetAwareListener {

    private int delayBetweenRetries;
    private int numberOfRetries;
    private int batchProcessingTimeout;
    private final Collection<MessageHandler> messageHandlers;
    private UnmarshallerService unmarshallerService;


    AbstractBaseKafkaListener(Collection<MessageHandler> messageHandlers) {
        super();
        this.messageHandlers = Collections.unmodifiableCollection(messageHandlers);
    }

    /**
     * Generic error Handling and logging, to be called by inheritors
     *
     * @param message raw Kafka Message
     * @param headers Headers reported by Spring kafka
     */

    public void onMessage(String message, MessageHeaders headers, Acknowledgment ack) {
        // logic to reliable reset consumer offset
        // this is only part of code that is bulletproof for this purpose
        removePartitionOffset(headers);

        final Long offset = headers.get(KafkaHeaders.OFFSET, Long.class);
        final Integer partition = headers.get(KafkaHeaders.RECEIVED_PARTITION, Integer.class);
        final String topic = headers.get(KafkaHeaders.RECEIVED_TOPIC, String.class);

//        log.info("Received message from TOPIC {} with OFFSET {}, PARTITION {}, and PAYLOAD '{}'", topic, offset,
//                partition, message);

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ErrorTemplate.handle(ErrorTemplate.withExecutionTimeout(batchProcessingTimeout, MILLISECONDS), () -> {
            final QiwkEvent event = unmarshallerService.unmarshal(message,
                    new TypeReference<QiwkEvent>() {
                    });
            return handle(event, headers);
        }).onError(MessageDeserializationException.class, ErrorTemplate.fail(),
                        e -> log.error("Failed to deserialize incoming message. Payload: {}", message, e))
                .onError(UnsupportedEventException.class, ErrorTemplate.warn(),
                        e -> log.warn("Message handling failed, not supported event received. Payload: {}", message, e))
                .onError(DataAccessException.class, ErrorTemplate.unlimitedRetry(delayBetweenRetries, MILLISECONDS),
                        e -> log.warn("Retrying until DataAccessException are gone.", e))
                .onError(CannotCreateTransactionException.class, ErrorTemplate.unlimitedRetry(delayBetweenRetries, MILLISECONDS),
                        e -> log.warn("Retrying until CannotCreateTransactionException are gone.", e))
                .onError(RecoverableException.class, ErrorTemplate.unlimitedRetry(delayBetweenRetries, MILLISECONDS),
                        e -> log.warn("Retrying until RecoverableException is gone.", e))
                .onError(MaybeRecoverableException.class, ErrorTemplate.limitedRetry(numberOfRetries, delayBetweenRetries, MILLISECONDS),
                        e -> log.warn("Retrying until MaybeRecoverableException is gone or number of trials exceeds limit", e))
                .onSuccess(result -> {

                    stopWatch.stop();
                    ack.acknowledge();
                    log.info("Message is processed in {} ms", stopWatch.getTotalTimeMillis());
                }).onFailure(e -> {

                    stopWatch.stop();
                    ack.acknowledge();
                    log.error("Message handling failed. Payload: {}", message, e);
                }).onWarning(e -> {
                    stopWatch.stop();
                    ack.acknowledge();
                    log.warn("Warning!!! Message handling failed. Payload: {}", message, e);
                }).onTimeout(() -> {

                    throw new MessageProcessingTimeoutException("Message processing time exceeds threshold");
                }).go();


    }

    /**
     * Handle incomming messages. Extension point to be implemented by inheritors
     *
     * @param event kafka message marshalled into SimpleApplicationEvent
     * @param headers message header
     * @return ProcessingResult
     */
    protected String handle(final QiwkEvent event, final MessageHeaders headers) {
        return messageHandlers.stream()
                .filter(messageHandler -> messageHandler.isSupported(event.getMetadata().getEventName()))
                .findFirst()
                .orElseThrow(() -> new UnsupportedEventException("Unsupported Event!!!"))
                .handle(event, headers);

    }

    /**
     * Setter injection. Inject UnmarshallerService
     *
     * @param unmarshallerService UnmarshallerService
     */
    @Autowired
    public void setUnMarshallerService(UnmarshallerService unmarshallerService) {
        this.unmarshallerService = unmarshallerService;
    }

    // Delay between retries if error covered by retrying strategy happens
    @Value("${config.kafka.consumer.retry.delay:10000}")
    public void setDelayBetweenRetries(int delayBetweenRetries) {
        this.delayBetweenRetries = delayBetweenRetries;
    }

    // Delay between retries if error covered by limitedRetry() strategy happens
    @Value("${config.kafka.consumer.retry.number:10}")
    public void setNumberOfRetries(int numberOfRetries) {
        this.numberOfRetries = numberOfRetries;
    }

    /**
     * The general need of this value in ErrorTemplate because
     * if you keep retrying for some time and even in the end it is successful,
     * if next poll() or commit will be later than given setting it will fail,
     * because broker will consider consumer thread as dead and
     * assign partition to other consumer thread or service
     *
     * @param batchProcessingTimeout
     */
    @Value("${spring.kafka.properties.max.poll.interval.ms:300000}")
    // defaults to kafka client default value - 5 min
    public void setBatchProcessingTimeout(int batchProcessingTimeout) {
        this.batchProcessingTimeout = batchProcessingTimeout;
    }

}
