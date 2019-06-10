package com.orness.gandalf.core.connector.connectorworkflowengineservice.grpc;

import com.orness.gandalf.core.connector.connectorworkflowengineservice.manager.ConnectorWorkflowEngineManager;
import com.orness.gandalf.core.module.connectorworkflowengineservice.grpc.*;
import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;

@GRpcService
public class ConnectorWorkflowEngineGrpc extends ConnectorWorkflowEngineServiceGrpc.ConnectorWorkflowEngineServiceImplBase {

    @Autowired
    private ConnectorWorkflowEngineManager connectorWorkflowEngineManager;

/*    public void subscribeTopicDefault(SubscribeRequest request, StreamObserver<DefaultResponse> responseObserver) {
        System.out.println("Request received from sample:\n" + request);

        Subscribe subscribe = request.getSubscribe();
        connectorWorkflowEngineManager.subscribeTopicBus(subscribe);

        DefaultResponse response = DefaultResponse.newBuilder().setMessage("Ok").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }*/

    public void subscribeTopicWorkflow(SubscribeRequest request, StreamObserver<DefaultResponse> responseObserver) {
        System.out.println("Request subscribeTopic received from sample:\n" + request);

        Subscribe subscribe = request.getSubscribe();
        connectorWorkflowEngineManager.subscribeTopicBus(subscribe);
        DefaultResponse response = DefaultResponse.newBuilder().setMessage("Ok").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

        connectorWorkflowEngineManager.getMessageStreamWorkflow(subscribe);
    }

    public void subscribeTopic(SubscribeRequest request, StreamObserver<MessageResponse> responseObserver) {
        System.out.println("Request subscribeOneTopic received from sample:\n" + request);

        Subscribe subscribe = request.getSubscribe();
        connectorWorkflowEngineManager.subscribeTopicBus(subscribe);
        //DefaultResponse response = DefaultResponse.newBuilder().setMessage("Ok").build();
        // MessageGandalf messageGandalfBus = connectorWorkflowEngineManager.getOneMessageStream(subscribe);

        Iterator<com.orness.gandalf.core.module.connectorbusservice.grpc.MessageResponse> messageResponseIterator = connectorWorkflowEngineManager.getMessageStream(subscribe);
        MessageGandalf messageGandalfBus = null;
        while(messageResponseIterator.hasNext()) {
            com.orness.gandalf.core.module.connectorbusservice.grpc.Message currentMessage =  messageResponseIterator.next().getMessage();
            System.out.println("MessageGandalf " + currentMessage);

            messageGandalfBus = new MessageGandalf(currentMessage.getTopic(),
                    currentMessage.getSender(),
                    currentMessage.getExpirationTime(),
                    currentMessage.getCreationDate(),
                    currentMessage.getContent());

            System.out.println("MESSAGE BUS");
            System.out.println(messageGandalfBus);

            com.orness.gandalf.core.module.connectorworkflowengineservice.grpc.Message.Builder builder = com.orness.gandalf.core.module.connectorworkflowengineservice.grpc.Message.newBuilder();
            builder.setTopic(messageGandalfBus.getTopic())
                    .setSender(messageGandalfBus.getSender())
                    .setCreationDate(messageGandalfBus.getCreationDate().toString())
                    .setExpirationTime(messageGandalfBus.getExpirationTime().toString())
                    .setContent(messageGandalfBus.getContent());
            com.orness.gandalf.core.module.connectorworkflowengineservice.grpc.Message message = builder.build();
            MessageResponse response = MessageResponse.newBuilder().setMessage(message).build();

            responseObserver.onNext(response);
        }

        responseObserver.onCompleted();
    }

    public void subscribeOneTopic(SubscribeRequest request, StreamObserver<MessageResponse> responseObserver) {
        System.out.println("Request subscribeOneTopic received from sample:\n" + request);

        Subscribe subscribe = request.getSubscribe();
        connectorWorkflowEngineManager.subscribeTopicBus(subscribe);
        //DefaultResponse response = DefaultResponse.newBuilder().setMessage("Ok").build();
       // MessageGandalf messageGandalfBus = connectorWorkflowEngineManager.getOneMessageStream(subscribe);

        Iterator<com.orness.gandalf.core.module.connectorbusservice.grpc.MessageResponse> messageResponseIterator = connectorWorkflowEngineManager.getMessageStream(subscribe);
        MessageGandalf messageGandalfBus = null;
        while(messageResponseIterator.hasNext()) {
            com.orness.gandalf.core.module.connectorbusservice.grpc.Message currentMessage =  messageResponseIterator.next().getMessage();
            System.out.println("MessageGandalf " + currentMessage);

            messageGandalfBus = new MessageGandalf(currentMessage.getTopic(),
                    currentMessage.getSender(),
                    currentMessage.getExpirationTime(),
                    currentMessage.getCreationDate(),
                    currentMessage.getContent());
            break;
        }

        System.out.println("MESSAGE BUS");
        System.out.println(messageGandalfBus);

        com.orness.gandalf.core.module.connectorworkflowengineservice.grpc.Message.Builder builder = com.orness.gandalf.core.module.connectorworkflowengineservice.grpc.Message.newBuilder();
        builder.setTopic(messageGandalfBus.getTopic())
                .setSender(messageGandalfBus.getSender())
                .setCreationDate(messageGandalfBus.getCreationDate().toString())
                .setExpirationTime(messageGandalfBus.getExpirationTime().toString())
                .setContent(messageGandalfBus.getContent());
        com.orness.gandalf.core.module.connectorworkflowengineservice.grpc.Message message = builder.build();
        MessageResponse response = MessageResponse.newBuilder().setMessage(message).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void unsubscribeTopic(SubscribeRequest request, StreamObserver<DefaultResponse> responseObserver) {
        System.out.println("Request unsubscribeTopic received from sample:\n" + request);

        Subscribe subscribe = request.getSubscribe();
        connectorWorkflowEngineManager.unsubscribeTopicBus(subscribe);
    }
}

