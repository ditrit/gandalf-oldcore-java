package com.orness.core.connectorbusservice.consumer;

import com.orness.core.messagebusmodule.domain.MessageBus;
import com.orness.core.workflowtopicmodule.domain.Topic;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

public class ConnectorBusListener {

    private final Topic topic;
    private CountDownLatch latch = new CountDownLatch(3);

    public Topic getTopic() {
        return topic;
    }

    public ConnectorBusListener(Topic topic) {
        this.topic = topic;
    }

    @KafkaListener(topics = "#{__listener.topic}", groupId = "#{__listener.topic}.group", containerFactory = "connectorMessageKafkaListenerContainerFactory")
    public void connectorMessageKafkaListener(MessageBus messageBus) {
        System.out.println("Received kafkaMessage message: " + messageBus.toString());
        this.latch.countDown();

        //ADD
        this.topic.getMessageBusLinkedList().add(messageBus);
    }
}
