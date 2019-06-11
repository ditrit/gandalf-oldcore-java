package com.orness.gandalf.core.connector.connectorbusservice.producer;

import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConnectorBusProducer {

    @Value("${gandalf.database.topic}")
    private String databaseTopic;

    @Autowired
    private KafkaTemplate<String, MessageGandalf> connectorMessageKafkaTemplate;

    public void sendConnectorMessageKafka(String topicName, MessageGandalf messageGandalf) {
        //Database Send
        connectorMessageKafkaTemplate.send(databaseTopic, messageGandalf);
        //Send
        connectorMessageKafkaTemplate.send(topicName, messageGandalf);
    }
}