package com.orness.core.javatestkafka1.consumer;

import com.orness.core.messagekafkamodule.domain.MessageKafka;
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
public class JavaTestKafka1ConsumerConfiguration {

    public ConsumerFactory<String, MessageKafka> javaMessageKafkaConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafkamessage");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(MessageKafka.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MessageKafka> javaMessageKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MessageKafka> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(javaMessageKafkaConsumerFactory());
        return factory;
    }
}
