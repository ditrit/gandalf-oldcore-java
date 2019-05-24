package com.orness.gandalf.core.databasebusservice.producer;

import com.orness.gandalf.core.messagebusmodule.domain.MessageBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class DatabaseBusProducer {


    @Autowired
    private KafkaTemplate<String, MessageBus> databaseMessageKafkaTemplate;

    private String topicName = "database";

    public void sendDatabaseMessageKafka(MessageBus messageBus) {
        databaseMessageKafkaTemplate.send(topicName, messageBus);
    }
}