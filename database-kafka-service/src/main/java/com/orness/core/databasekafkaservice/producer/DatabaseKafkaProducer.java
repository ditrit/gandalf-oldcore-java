package com.orness.core.databasekafkaservice.producer;

import com.orness.core.messagekafkamodule.domain.MessageKafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class DatabaseKafkaProducer {


    @Autowired
    private KafkaTemplate<String, MessageKafka> databaseMessageKafkaTemplate;

    private String topicName = "database";

    public void sendDatabaseMessageKafka(MessageKafka messageKafka) {
        databaseMessageKafkaTemplate.send(topicName, messageKafka);
    }
}