package com.orness.core.connectorkafkaservice.consumer;

import com.orness.core.messagekafkamodule.domain.MessageKafka;
import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class ConnectorKafkaListener {

    @Autowired
    private ZeebeClient zeebeClient;

    private final String topic;
    private CountDownLatch latch = new CountDownLatch(3);

    public String getTopic() {
        return topic;
    }

    public ConnectorKafkaListener(String topic) {
        this.topic = topic;
    }

    @KafkaListener(topics = "#{__listener.topic}", groupId = "#{__listener.topic}.group", containerFactory = "connectorMessageKafkaListenerContainerFactory")
    public void connectorMessageKafkaListener(MessageKafka messageKafka) {
        System.out.println("Received kafkaMessage message: " + messageKafka.toString());
        this.latch.countDown();

        zeebeClient.newPublishMessageCommand()
                .messageName("message")
                .correlationKey(messageKafka.getWorkflow_content())
                //.variables()
                .timeToLive(Duration.ofMinutes(1))
                .send().join();
    }
}
