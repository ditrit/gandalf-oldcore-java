package com.orness.core.javatestkafka2.consumer;

import com.orness.core.connectorbusservice.domain.ConnectorKafkaServiceGrpc;
import com.orness.core.connectorbusservice.domain.MessageRequest;
import com.orness.core.connectorbusservice.domain.MessageResponse;
import com.orness.core.messagebusmodule.domain.MessageKafka;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class JavaTestKafka2Consumer {

    public CountDownLatch latch = new CountDownLatch(3);

    //JAVA TO JAVA
    @KafkaListener(topics = "zeebeJ2J", groupId = "kafkamessage", containerFactory = "javaMessageKafkaListenerContainerFactory")
    public void connectorMessageKafkaListenerJ2J(MessageKafka messageKafka) {
        System.out.println("Received kafkaMessage message: " + messageKafka.toString());
        this.latch.countDown();

        //Send message to ConnectorKafka
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 10000).usePlaintext().build();
        ConnectorKafkaServiceGrpc.ConnectorKafkaServiceBlockingStub stub = ConnectorKafkaServiceGrpc.newBlockingStub(channel);
        MessageResponse messageResponse = stub.message(MessageRequest.newBuilder()
                .setId(messageKafka.getWorkflow_id())
                .setName(messageKafka.getWorkflow_name())
                .setTopic(messageKafka.getWorkflow_topic())
                .setContent("zeebeJ2W")
                .build());

        System.out.println("Response received from server:\n" + messageResponse);
        channel.shutdown();
    }
}

