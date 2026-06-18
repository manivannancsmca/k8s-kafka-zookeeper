package com.k8s_kafka_zookeeper_producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
class KafkaTopicConfig {
    
    @Bean
    public NewTopic exploreTopic() {
        return TopicBuilder.name("my-explore-topic")
                .partitions(3)
                .replicas(3) // Configured for your 3-broker cluster
                .build();
    }
}
