package com.orness.gandalf.core.connector.databasebusservice.consumer;

import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import com.orness.gandalf.core.module.messagemodule.repository.MessageGandalfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class DatabaseBusConsumer {

    private final MessageGandalfRepository messageGandalfRepository;
    public CountDownLatch latch = new CountDownLatch(3);

    @Autowired
    public DatabaseBusConsumer(MessageGandalfRepository messageGandalfRepository, @Value("${gandalf.database.topic}") String topic, @Value("${gandalf.database.group}") String group) {
        this.messageGandalfRepository = messageGandalfRepository;
    }

    @KafkaListener(topics = "#{'${gandalf.database.topic}'}", groupId = "#{'${gandalf.database.group}'}", containerFactory = "databaseMessageKafkaListenerContainerFactory")
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

