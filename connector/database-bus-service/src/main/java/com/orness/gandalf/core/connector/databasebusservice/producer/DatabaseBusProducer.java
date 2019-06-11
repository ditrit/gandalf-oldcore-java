package com.orness.gandalf.core.connector.databasebusservice.producer;

import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class DatabaseBusProducer {


    @Autowired
    private KafkaTemplate<String, MessageGandalf> databaseMessageKafkaTemplate;

    @Value("${gandalf.database.topic}")
    private String topic;

    public void sendDatabaseMessageKafka(MessageGandalf messageGandalf) {
        databaseMessageKafkaTemplate.send(topic, messageGandalf);
    }
}