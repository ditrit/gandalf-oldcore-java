package com.orness.gandalf.core.connector.connectorbusservice.other.kafka.producer;

import com.orness.gandalf.core.module.messagemodule.gandalf.domain.GandalfMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class ConnectorBusProducer {

    @Value("${gandalf.database.topic}")
    private String databaseTopic;

    @Autowired
    private KafkaTemplate<String, GandalfMessage> connectorMessageKafkaTemplate;

    public void sendConnectorMessageKafka(String topicName, GandalfMessage gandalfMessage) {
        //Database Send
        connectorMessageKafkaTemplate.send(databaseTopic, gandalfMessage);
        //Send
        connectorMessageKafkaTemplate.send(topicName, gandalfMessage);
    }
}