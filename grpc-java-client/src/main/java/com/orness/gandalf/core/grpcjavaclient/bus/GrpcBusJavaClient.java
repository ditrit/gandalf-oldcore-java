package com.orness.gandalf.core.grpcjavaclient.bus;

import com.orness.gandalf.core.connectorbusservice.grpc.*;
import com.orness.gandalf.core.messagebusmodule.domain.MessageBus;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

public class GrpcBusJavaClient {

    private ManagedChannel channel;
    private ConnectorBusServiceGrpc.ConnectorBusServiceBlockingStub stub;

    private int port = 10001;

    public GrpcBusJavaClient() {
        startClient();
    }

    public void startClient() {
        channel = ManagedChannelBuilder.forAddress("localhost", port)
                .usePlaintext()
                .build();

        stub = ConnectorBusServiceGrpc.newBlockingStub(channel);
    }

    public void stopClient() {
        channel.shutdown();
    }

    public void sendMessage(String topic, String sender, String content) {
        Message.Builder builder = Message.newBuilder();
        builder.setTopic(topic)
                .setSender(sender)
                .setExpirationTime("2018-09-01 09:01:15")
                .setCreationDate("2014-10-21")
                .setContent(content);
        Message message = builder.build();

        MessageRequest request = MessageRequest.newBuilder().setMessage(message).build();
        System.out.println("SEND " + topic);
        System.out.println(stub.sendMessage(request));
    }

    public void createTopic(String topicName) {
        Topic.Builder builder = Topic.newBuilder();
        builder.setName(topicName)
                .setNumPartitions("1")
                .setReplicationFactor("1");
        Topic topic = builder.build();
        System.out.println("CREATE " + topic);
        TopicRequest request = TopicRequest.newBuilder().setTopic(topic).build();
        System.out.println(stub.createTopic(request));
    }

    public MessageBus getMessage(String topic, String receiver) {
        GetMessage.Builder builder = GetMessage.newBuilder();
        builder.setTopic(topic)
                .setSubscriber(receiver);
        GetMessage getMessage = builder.build();
        System.out.println("getMessage " + topic + " " + receiver);
        GetMessageRequest request = GetMessageRequest.newBuilder().setMessage(getMessage).build();
        MessageResponse messageResponse = stub.getMessage(request);
        System.out.println(messageResponse);
        MessageBus response = null;
        if(messageResponse.getMessage() != null) {
            response = new MessageBus(messageResponse.getMessage().getTopic(),
                    messageResponse.getMessage().getSender(),
                    messageResponse.getMessage().getExpirationTime(),
                    messageResponse.getMessage().getCreationDate(),
                    messageResponse.getMessage().getContent());
        }
        return response;
    }

    public Iterator<MessageResponse> getMessageStream(String topic, String receiver) {
        Iterator<MessageResponse> messageResponseIterator;
        GetMessage.Builder builder = GetMessage.newBuilder();
        builder.setTopic(topic)
                .setSubscriber(receiver);
        GetMessage getMessage = builder.build();
        System.out.println("getMessageStream " + topic + " " + receiver);
        GetMessageRequest request = GetMessageRequest.newBuilder().setMessage(getMessage).build();
        messageResponseIterator = stub.getMessageStream(request);
        return messageResponseIterator;
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
