package com.orness.gandalf.core.service.databasebusservice.topic;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DatabaseBusTopicConfiguration {

    @Value("${gandalf.database.broker}")
    private String brokerAddress;

    @Value("${gandalf.database.topic}")
    private String topic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicDataBaseMessageKafka() {
        return new NewTopic(topic, 1, (short) 1);
    }

}
