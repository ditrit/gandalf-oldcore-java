package com.orness.gandalf.core.connector.databasebusservice.consumer;

import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class DatabaseBusConsumerConfiguration {

    public ConsumerFactory<String, MessageGandalf> databaseMessageKafkaConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafkamessage");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(MessageGandalf.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MessageGandalf> databaseMessageKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MessageGandalf> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(databaseMessageKafkaConsumerFactory());
        return factory;
    }
}
