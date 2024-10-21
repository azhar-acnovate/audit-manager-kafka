package com.acnovate.kafka.consumer.AuditManager.listener;

import com.acnovate.kafka.consumer.AuditManager.config.KafkaOffsetConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;


/**
 * @author Shubham Angachekar
 * @see org.springframework.kafka.listener.ConsumerSeekAware
 */
@Slf4j
public class KafkaSeekOffsetAwareListener implements ConsumerSeekAware {

    private final ThreadLocal<ConsumerSeekAware.ConsumerSeekCallback> seekCallBack = new ThreadLocal<>();

    private KafkaOffsetConfiguration offsetConfig;

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerSeekCallback(ConsumerSeekCallback callback) {
        log.info("Registering seek callback");
        this.seekCallBack.set(callback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        log.info("Incoming Topic Partitions {}", assignments);

        assignments.forEach((TopicPartition assignment, Long assignmentKey) -> {
            final String topic = assignment.topic();
            final int partition = assignment.partition();

            offsetConfig.getPartitionStartOffset(topic, partition).ifPresent((Long startOffset) -> {
                log.info("Setting initial offset to {} for Topic {} and Partition {}", startOffset, topic, partition);
                this.seekCallBack.get().seek(topic, partition, startOffset);
            });
        });
    }

    /**
     * Should be used to clean up offset reset config when Listener starts to process
     * messages. It means that consumer keeps assignment after seek back to desired
     * offset.
     *
     * @param headers headers of message received
     */
    public void removePartitionOffset(MessageHeaders headers) {
        final String topic = headers.get(KafkaHeaders.RECEIVED_TOPIC, String.class);
        final Integer partition = headers.get(KafkaHeaders.RECEIVED_PARTITION, Integer.class);
        offsetConfig.removePartitionStartOffset(topic, partition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onIdleContainer(Map<TopicPartition, Long> assignments, ConsumerSeekAware.ConsumerSeekCallback callback) {
        log.info("Container idle");
    }

    /**
     * Sets offset config.
     *
     * @param offsetConfig the offset config
     */
    @Autowired
    public void setOffsetConfig(KafkaOffsetConfiguration offsetConfig) {
        this.offsetConfig = offsetConfig;
    }
}

