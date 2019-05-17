package com.orness.core.connectorworkflowengineservice.grpc;

import com.orness.core.connectorbusservice.grpc.*;
import com.orness.core.connectorbusservice.grpc.DefaultRequest;
import com.orness.core.connectorbusservice.grpc.DefaultResponse;
import com.orness.core.connectorbusservice.grpc.Message;
import com.orness.core.connectorbusservice.grpc.MessageRequest;
import com.orness.core.connectorbusservice.grpc.MessageResponse;
import com.orness.core.connectorbusservice.grpc.Subscribe;
import com.orness.core.connectorbusservice.grpc.SubscribeRequest;
import com.orness.core.connectorbusservice.grpc.Topic;
import com.orness.core.connectorbusservice.grpc.TopicRequest;
import com.orness.core.connectorworkflowengineservice.workflow.ConnectorWorkflowEngine;
import com.orness.core.messageworkflowenginemodule.domain.MessageWorkflowEngine;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

public class ConnectorWorkflowEngineBusGrpc {

    private ManagedChannel channel;
    private ConnectorBusServiceGrpc.ConnectorBusServiceBlockingStub stub;
    private ConnectorWorkflowEngine connectorWorkflowEngine;

    public ConnectorWorkflowEngineBusGrpc(ConnectorWorkflowEngine connectorWorkflowEngine) {
        this.connectorWorkflowEngine = connectorWorkflowEngine;
        StartClient();
    }

    public void StartClient() {
        channel = ManagedChannelBuilder.forAddress("localhost", 9005)
                .usePlaintext()
                .build();

        stub = ConnectorBusServiceGrpc.newBlockingStub(channel);
    }

    private void StopClient() {
        channel.shutdown();
    }


   /* private void sendMessage(String id, String name, String topic, String content) {
        Message.Builder builder = Message.newBuilder();
        builder.setId(id)
                .setName(name)
                .setTopic(topic)
                .setContent(content);

        Message message = builder.build();
        //REQUEST
        MessageRequest messageRequest = MessageRequest.newBuilder().setMessage(message).build();
        //CALL RPC
        DefaultResponse response = stub.sendMessage(messageRequest);
        //PRINT
        System.out.println("Response received from server:\n" + response);
    }

    public void sendMessageStream(String id, String name, String topic, String content) {
        Iterator<MessageResponse> messageResponseIterator;
        Message.Builder builder = Message.newBuilder();
        builder.setId(id)
                .setName(name)
                .setTopic(topic)
                .setContent(content);

        Message message = builder.build();
        //REQUEST
        MessageRequest messageRequest = MessageRequest.newBuilder().setMessage(message).build();
        //CALL RPC
        messageResponseIterator = stub.sendMessageStream(messageRequest);
        //PRINT
        while (messageResponseIterator.hasNext()) {
            System.out.println("Response received from server:\n" + messageResponseIterator.next());

        }
    }

    public void createTopic(String id, String numPartitions, String replicationFactor) {
        Topic.Builder builder = Topic.newBuilder();
        builder.setName(id)
                .setNumPartitions(numPartitions)
                .setReplicationFactor(replicationFactor);

        Topic topic = builder.build();
        //REQUEST
        TopicRequest topicRequest = TopicRequest.newBuilder().setTopic(topic).build();
        //CALL RPC
        DefaultResponse response = stub.createTopic(topicRequest);
        //PRINT
        System.out.println("Response received from server:\n" + response);
    }*/

    public void subscribeTopic(String workflow_id, String workflow_name, String topic) {
        Iterator<MessageResponse> messageResponseIterator;

        Subscribe.Builder builder = Subscribe.newBuilder();
        builder.setId(workflow_id)
                .setName(workflow_name)
                .setTopic(topic);

        Subscribe subscribe = builder.build();

        //Request
        SubscribeRequest subscribeRequest = SubscribeRequest.newBuilder().setSubscribe(subscribe).build();
        messageResponseIterator = stub.subscribeTopic(subscribeRequest);
        while(messageResponseIterator.hasNext()) {
            //CURRENT RESPONSE
            MessageResponse messageResponse = messageResponseIterator.next();
            //
            MessageWorkflowEngine messageWorkflowEngine = new MessageWorkflowEngine(messageResponse.getMessage().getIdSender(),
                    messageResponse.getMessage().getName(),
                    messageResponse.getMessage().getTopic(),
                    messageResponse.getMessage().getCreationDate(),
                    messageResponse.getMessage().getExpirationTime(),
                    messageResponse.getMessage().getContent());
            //
            connectorWorkflowEngine.sendMessageWorkflowEngine(messageWorkflowEngine);
        }
    }

    public void unsubscribeTopic(String workflow_id, String workflow_name, String topic) {
        Subscribe.Builder builder = Subscribe.newBuilder();
        builder.setId(workflow_id)
                .setName(workflow_name)
                .setTopic(topic);

        Subscribe subscribe = builder.build();

        //Request
        SubscribeRequest subscribeRequest = SubscribeRequest.newBuilder().setSubscribe(subscribe).build();
        DefaultResponse defaultResponse = stub.unsubscribeTopic(subscribeRequest);

    }
}
