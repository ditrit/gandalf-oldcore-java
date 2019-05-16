package com.orness.core.databasekafkaservice.consumer;

import com.orness.core.messagekafkamodule.domain.MessageKafka;
import com.orness.core.messagekafkamodule.repository.MessageKafkaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class DatabaseKafkaConsumer {

    private final MessageKafkaRepository messageKafkaRepository;
    public CountDownLatch latch = new CountDownLatch(3);
    private final String topicName = "database";

    @Autowired
    public DatabaseKafkaConsumer(MessageKafkaRepository messageKafkaRepository) {
        this.messageKafkaRepository = messageKafkaRepository;
    }

    @KafkaListener(topics = topicName, groupId = "database", containerFactory = "databaseMessageKafkaListenerContainerFactory")
    public void databaseMessageKafkaListener(MessageKafka messageKafka) {
        System.out.println("Received kafkaMessage message: " + messageKafka);
        this.latch.countDown();
        //Save
        messageKafkaRepository.save(messageKafka);

        //Print
        List<MessageKafka> messageKafkaList = messageKafkaRepository.findAll();
        messageKafkaList.stream().forEach(System.out::println);
    }

}

