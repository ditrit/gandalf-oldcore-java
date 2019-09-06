package com.orness.gandalf.core.service.gandalfdatabaseservice.producer;

import com.orness.gandalf.core.module.messagemodule.gandalf.domain.GandalfMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class DatabaseBusProducer {


    @Autowired
    private KafkaTemplate<String, GandalfMessage> databaseMessageKafkaTemplate;

    @Value("${gandalf.database.topic}")
    private String topic;

    public void sendDatabaseMessageKafka(GandalfMessage gandalfMessage) {
        databaseMessageKafkaTemplate.send(topic, gandalfMessage);
    }
}