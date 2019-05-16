package com.orness.core.javatestkafka1.consumer;

import com.orness.core.connectorkafkaservice.domain.ConnectorKafkaServiceGrpc;
import com.orness.core.connectorkafkaservice.domain.MessageRequest;
import com.orness.core.connectorkafkaservice.domain.MessageResponse;
import com.orness.core.messagekafkamodule.domain.MessageKafka;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class JavaTestKafka1Consumer {

    public CountDownLatch latch = new CountDownLatch(3);

    //WORKFLOW TO JAVA
    @KafkaListener(topics = "zeebeW2J", groupId = "kafkamessage", containerFactory = "javaMessageKafkaListenerContainerFactory")
    public void connectorMessageKafkaListenerW2J(MessageKafka messageKafka) {
        System.out.println("Received kafkaMessage message: " + messageKafka.toString());
        this.latch.countDown();

        //Send message to ConnectorKafka
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 10000).usePlaintext().build();
        ConnectorKafkaServiceGrpc.ConnectorKafkaServiceBlockingStub stub = ConnectorKafkaServiceGrpc.newBlockingStub(channel);
        MessageResponse messageResponse = stub.message(MessageRequest.newBuilder()
                .setId(messageKafka.getWorkflow_id())
                .setName(messageKafka.getWorkflow_name())
                .setTopic(messageKafka.getWorkflow_topic())
                .setContent("zeebeJ2J")
                .build());

        System.out.println("Response received from server:\n" + messageResponse);
        channel.shutdown();
    }

}

