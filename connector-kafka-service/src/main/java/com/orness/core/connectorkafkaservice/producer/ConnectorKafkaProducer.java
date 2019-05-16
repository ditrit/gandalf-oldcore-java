package com.orness.core.connectorkafkaservice.producer;

import com.orness.core.messagekafkamodule.domain.MessageKafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConnectorKafkaProducer {

    @Autowired
    private KafkaTemplate<String, MessageKafka> connectorMessageKafkaTemplate;

    public void sendConnectorMessageKafka(String topicName, MessageKafka messageKafka) {
        //Database Send
        connectorMessageKafkaTemplate.send("database", messageKafka);
        //Send
        connectorMessageKafkaTemplate.send(topicName, messageKafka);
    }
}