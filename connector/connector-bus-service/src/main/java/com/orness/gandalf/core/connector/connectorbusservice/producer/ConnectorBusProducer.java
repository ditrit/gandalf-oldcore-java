package com.orness.gandalf.core.connector.connectorbusservice.producer;

import com.orness.gandalf.core.module.messagebusmodule.domain.MessageBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConnectorBusProducer {

    @Autowired
    private KafkaTemplate<String, MessageBus> connectorMessageKafkaTemplate;

    public void sendConnectorMessageKafka(String topicName, MessageBus messageBus) {
        //Database Send
        connectorMessageKafkaTemplate.send("database", messageBus);
        //Send
        connectorMessageKafkaTemplate.send(topicName, messageBus);
    }
}