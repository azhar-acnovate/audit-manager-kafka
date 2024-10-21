package com.acnovate.kafka.consumer.AuditManager.config;


import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Kafka offset properties coming from external config server in format
 * <p>
 * kafka: consumer: topicNameToPartitionOffset: "[topic_name]": 0: 0 1: 0 2: 0
 * 3: 0
 *
 * @author Shubham Angachekar
 */
@Getter
@Configuration
@ConfigurationProperties(prefix = "config.kafka.consumer")
@Slf4j
@Data
public class KafkaOffsetConfiguration {

    // This attribute name should match the configuration variable name under
    // config.kafka.consumer to be injected correctly
    private final Map<String, ConcurrentHashMap<Integer, Long>> topicNameToPartitionOffset = new HashMap<>();

    /**
     * Retrieves start offset for topic partition
     *
     * @param topic     the name of the topic in Kafka
     * @param partition index of the Kafka partition
     * @return optionally returns offset for specific Kafka partition
     */
    public Optional<Long> getPartitionStartOffset(String topic, int partition) {
        Optional<Long> result = Optional.empty();
        if (topicNameToPartitionOffset.containsKey(topic)) {
            result = Optional.ofNullable(topicNameToPartitionOffset.get(topic).get(partition));
        }
        return result;
    }

    /**
     * Removes start offset for given topic and partition.
     * <p>
     * The purpose of removal is to consume from the current offset when partitions
     * are reassigned due to scaling or some events from coordinator.
     * </p>
     *
     * @param topic     the name of the topic in Kafka
     * @param partition index of the Kafka partition
     */
    public void removePartitionStartOffset(String topic, int partition) {
        Optional.ofNullable(topicNameToPartitionOffset.get(topic)).ifPresent(
                partitionOffsets -> Optional.ofNullable(partitionOffsets.get(partition)).ifPresent(offset -> {
                    log.info("Cleaning initial offset configuration for Topic {} Partition {} Offset {}", topic,
                            partition, offset);
                    partitionOffsets.remove(partition);
                }));
    }

}
