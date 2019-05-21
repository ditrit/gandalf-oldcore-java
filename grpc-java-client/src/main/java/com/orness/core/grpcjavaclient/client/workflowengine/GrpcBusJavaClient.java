package com.orness.core.grpcjavaclient.client.workflowengine;

import com.orness.core.connectorbusservice.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.sql.Date;
import java.sql.Timestamp;

public class GrpcBusJavaClient {

    private static ManagedChannel channel;
    private static ConnectorBusServiceGrpc.ConnectorBusServiceBlockingStub stub;

    public static void main(String[] args) {
        StartClient();
        createTopic();
        System.out.println("Subs");
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("GET");
        sendMessage();
    }

    public static void StartClient() {
        channel = ManagedChannelBuilder.forAddress("localhost", 10000)
                .usePlaintext()
                .build();

        stub = ConnectorBusServiceGrpc.newBlockingStub(channel);
    }

    private static void StopClient() {
        channel.shutdown();
    }

    private static void sendMessage() {
        //MESSAGE REQUEST -> DEFAULT RESPONSE
        Message.Builder builder = Message.newBuilder();
        builder.setIdSender("tata")
                .setTopic("toto")
                .setName("toto")
                .setExpirationTime("2018-09-01 09:01:15")
                .setCreationDate("2014-10-21")
                .setContent("");
        Message message = builder.build();

        MessageRequest request = MessageRequest.newBuilder().setMessage(message).build();
        System.out.println(stub.sendMessage(request));
    }

    private static void createTopic() {
        //TOPIC REQUEST -> DEFAULT RESPONSE
        Topic.Builder builder = Topic.newBuilder();
        builder.setName("toto")
                .setNumPartitions("1")
                .setReplicationFactor("1");
        Topic topic = builder.build();

        TopicRequest request = TopicRequest.newBuilder().setTopic(topic).build();
        System.out.println(stub.createTopic(request));
    }
}
