package com.orness.gandalf.core.library.grpcjavaclient.workflowengine;

import com.orness.gandalf.core.module.connectorworkflowengineservice.grpc.*;
import com.orness.gandalf.core.module.connectorworkflowengineservice.grpc.ConnectorWorkflowEngineServiceGrpc;
import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
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

    public void subscribeTopicWorkflow(String topic, String subscriber) {
        Subscribe.Builder builder = Subscribe.newBuilder();
        builder.setTopic(topic)
                .setSubscriber(subscriber);
        Subscribe subscribe = builder.build();
        System.out.println("subscribeTopic " + topic + " " + subscriber);
        SubscribeRequest request = SubscribeRequest.newBuilder().setSubscribe(subscribe).build();
        System.out.println(stub.subscribeTopicWorkflow(request));
    }

    public Iterator<MessageResponse> subscribeTopic(String topic, String subscriber) {
        Subscribe.Builder builder = Subscribe.newBuilder();
        builder.setTopic(topic)
                .setSubscriber(subscriber);
        Subscribe subscribe = builder.build();
        System.out.println("subscribeOneTopic " + topic + " " + subscriber);
        SubscribeRequest request = SubscribeRequest.newBuilder().setSubscribe(subscribe).build();
        Iterator<MessageResponse> messageResponseIterator = stub.subscribeTopic(request);
        return messageResponseIterator;

    }

    public MessageGandalf subscribeOneTopic(String topic, String subscriber) {
        Subscribe.Builder builder = Subscribe.newBuilder();
        builder.setTopic(topic)
                .setSubscriber(subscriber);
        Subscribe subscribe = builder.build();
        System.out.println("subscribeOneTopic " + topic + " " + subscriber);
        SubscribeRequest request = SubscribeRequest.newBuilder().setSubscribe(subscribe).build();
        Iterator<MessageResponse> messageResponseIterator = stub.subscribeOneTopic(request);
        com.orness.gandalf.core.module.connectorworkflowengineservice.grpc.Message message = messageResponseIterator.next().getMessage();
        return new MessageGandalf(message.getTopic(), message.getSender(), message.getExpirationTime(), message.getCreationDate(), message.getContent());

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
