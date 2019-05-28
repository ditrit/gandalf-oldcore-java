package com.orness.gandalf.core.library.grpcjavaclient.workflowengine;

import com.orness.gandalf.core.module.connectorworkflowengineservice.grpc.*;
import com.orness.gandalf.core.module.connectorworkflowengineservice.grpc.ConnectorWorkflowEngineServiceGrpc;
import com.orness.gandalf.core.module.messagebusmodule.domain.MessageBus;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

public class GrpcWorkflowEngineJavaClient {

    private ManagedChannel channel;
    private ConnectorWorkflowEngineServiceGrpc.ConnectorWorkflowEngineServiceBlockingStub stub;
    private int port = 10003;


    public GrpcWorkflowEngineJavaClient() {
        startClient();
    }

    public void startClient() {
        channel = ManagedChannelBuilder.forAddress("localhost", port)
                .usePlaintext()
                .build();

        stub = ConnectorWorkflowEngineServiceGrpc.newBlockingStub(channel);
    }

    public void stopClient() {
        channel.shutdown();
    }

    public void subscribeTopic(String topic, String subscriber) {
        Subscribe.Builder builder = Subscribe.newBuilder();
        builder.setTopic(topic)
                .setSubscriber(subscriber);
        Subscribe subscribe = builder.build();
        System.out.println("subscribeTopic " + topic + " " + subscriber);
        SubscribeRequest request = SubscribeRequest.newBuilder().setSubscribe(subscribe).build();
        System.out.println(stub.subscribeTopic(request));
    }

    public MessageBus subscribeOneTopic(String topic, String subscriber) {
        Subscribe.Builder builder = Subscribe.newBuilder();
        builder.setTopic(topic)
                .setSubscriber(subscriber);
        Subscribe subscribe = builder.build();
        System.out.println("subscribeOneTopic " + topic + " " + subscriber);
        SubscribeRequest request = SubscribeRequest.newBuilder().setSubscribe(subscribe).build();
        Iterator<MessageResponse> messageResponseIterator = stub.subscribeOneTopic(request);
        Message message = messageResponseIterator.next().getMessage();
        return new MessageBus(message.getTopic(), message.getSender(), message.getExpirationTime(), message.getCreationDate(), message.getContent());

    }

    public void unsubscribeTopic(String topic, String subscriber) {
        Subscribe.Builder builder = Subscribe.newBuilder();
        builder.setTopic(topic)
                .setSubscriber(subscriber);
        Subscribe subscribe = builder.build();
        System.out.println("unsubscribeTopic " + topic + " " + subscriber);
        SubscribeRequest request = SubscribeRequest.newBuilder().setSubscribe(subscribe).build();
        System.out.println(stub.unsubscribeTopic(request));
    }
}
