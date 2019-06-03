package com.orness.gandalf.core.connector.databasebusservice.producer;

import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class DatabaseBusProducer {


    @Autowired
    private KafkaTemplate<String, MessageGandalf> databaseMessageKafkaTemplate;

    private String topicName = "database";

    public void sendDatabaseMessageKafka(MessageGandalf messageGandalf) {
        databaseMessageKafkaTemplate.send(topicName, messageGandalf);
    }
}