package com.orness.gandalf.core.module.kafkamodule.core.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "kafka")
public class ConnectorKafkaProducer {

    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public ConnectorKafkaProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendKafka(String topic, Object message) {
        kafkaTemplate.send(topic, message);
    }
}
