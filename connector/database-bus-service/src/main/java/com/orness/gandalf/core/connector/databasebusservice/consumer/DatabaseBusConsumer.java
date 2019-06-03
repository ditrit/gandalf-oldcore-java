package com.orness.gandalf.core.connector.databasebusservice.consumer;

import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import com.orness.gandalf.core.module.messagemodule.repository.MessageGandalfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class DatabaseBusConsumer {

    private final MessageGandalfRepository messageGandalfRepository;
    public CountDownLatch latch = new CountDownLatch(3);
    private final String topicName = "database";

    @Autowired
    public DatabaseBusConsumer(MessageGandalfRepository messageGandalfRepository) {
        this.messageGandalfRepository = messageGandalfRepository;
    }

    @KafkaListener(topics = topicName, groupId = "database", containerFactory = "databaseMessageKafkaListenerContainerFactory")
    public void databaseMessageKafkaListener(MessageGandalf messageGandalf) {
        System.out.println("Received kafkaMessage messageGandalf: " + messageGandalf);
        this.latch.countDown();
        //Save
        messageGandalfRepository.save(messageGandalf);

        //Print
        List<MessageGandalf> messageGandalfList = messageGandalfRepository.findAll();
        messageGandalfList.stream().forEach(System.out::println);
    }

}

