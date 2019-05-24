package com.orness.gandalf.core.connectorworkflowengineservice.grpc;

import com.orness.gandalf.core.connectorworkflowengineservice.manager.ConnectorWorkflowEngineManager;
import com.orness.gandalf.core.messagebusmodule.domain.MessageBus;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

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

    public void subscribeTopic(SubscribeRequest request, StreamObserver<DefaultResponse> responseObserver) {
        System.out.println("Request subscribeTopic received from sample:\n" + request);

        Subscribe subscribe = request.getSubscribe();
        connectorWorkflowEngineManager.subscribeTopicBus(subscribe);
        DefaultResponse response = DefaultResponse.newBuilder().setMessage("Ok").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

        connectorWorkflowEngineManager.getMessageStream(subscribe);
    }

    public void subscribeOneTopic(SubscribeRequest request, StreamObserver<MessageResponse> responseObserver) {
        System.out.println("Request subscribeOneTopic received from sample:\n" + request);

        Subscribe subscribe = request.getSubscribe();
        connectorWorkflowEngineManager.subscribeTopicBus(subscribe);
        //DefaultResponse response = DefaultResponse.newBuilder().setMessage("Ok").build();
        MessageBus messageBus = connectorWorkflowEngineManager.getOneMessageStream(subscribe);

        System.out.println("MESSAGE BUS");
        System.out.println(messageBus);

        Message.Builder builder = Message.newBuilder();
        builder.setTopic(messageBus.getTopic())
                .setSender(messageBus.getSender())
                .setCreationDate(messageBus.getCreationDate().toString())
                .setExpirationTime(messageBus.getExpirationTime().toString())
                .setContent(messageBus.getContent());
        Message message = builder.build();
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

