package com.orness.gandalf.core.service.gandalfdatabaseservice.consumer;

import com.orness.gandalf.core.module.messagemodule.gandalf.domain.GandalfMessage;
import com.orness.gandalf.core.module.messagemodule.gandalf.repository.GandalfMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class DatabaseBusConsumer {

    private final GandalfMessageRepository gandalfMessageRepository;
    public CountDownLatch latch = new CountDownLatch(3);

    @Autowired
    public DatabaseBusConsumer(GandalfMessageRepository gandalfMessageRepository, @Value("${gandalf.database.topic}") String topic, @Value("${gandalf.database.group}") String group) {
        this.gandalfMessageRepository = gandalfMessageRepository;
    }

    @KafkaListener(topics = "#{'${gandalf.database.topic}'}", groupId = "#{'${gandalf.database.group}'}", containerFactory = "databaseMessageKafkaListenerContainerFactory")
    public void databaseMessageKafkaListener(GandalfMessage gandalfMessage) {
        System.out.println("Received kafkaMessage gandalfMessage: " + gandalfMessage);
        this.latch.countDown();
        //Save
        gandalfMessageRepository.save(gandalfMessage);

        //Print
        List<GandalfMessage> gandalfMessageList = gandalfMessageRepository.findAll();
        gandalfMessageList.stream().forEach(System.out::println);
    }

}

