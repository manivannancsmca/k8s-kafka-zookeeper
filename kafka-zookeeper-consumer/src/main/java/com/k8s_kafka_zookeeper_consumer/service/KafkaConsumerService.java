package com.k8s_kafka_zookeeper_consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "my-explore-topic", groupId = "explore-clean-group")
    public void consume(String message) {
        log.info("#### -> Consumed message -> {}", message);
    }
}
