package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.core.producer;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.properties.ConnectorKafkaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(ConnectorKafkaProperties.class)
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
