package com.orness.core.databasebusservice.consumer;

import com.orness.core.messagebusmodule.domain.MessageBus;
import com.orness.core.messagebusmodule.repository.MessageBusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class DatabaseBusConsumer {

    private final MessageBusRepository messageBusRepository;
    public CountDownLatch latch = new CountDownLatch(3);
    private final String topicName = "database";

    @Autowired
    public DatabaseBusConsumer(MessageBusRepository messageBusRepository) {
        this.messageBusRepository = messageBusRepository;
    }

    @KafkaListener(topics = topicName, groupId = "database", containerFactory = "databaseMessageKafkaListenerContainerFactory")
    public void databaseMessageKafkaListener(MessageBus messageBus) {
        System.out.println("Received kafkaMessage message: " + messageBus);
        this.latch.countDown();
        //Save
        messageBusRepository.save(messageBus);

        //Print
        List<MessageBus> messageBusList = messageBusRepository.findAll();
        messageBusList.stream().forEach(System.out::println);
    }

}

