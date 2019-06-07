package com.orness.gandalf.core.connector.connectorbusservice.grpc;

import com.orness.gandalf.core.connector.connectorbusservice.manager.ConnectorBusManager;
import com.orness.gandalf.core.connector.connectorbusservice.producer.ConnectorBusProducer;
import com.orness.gandalf.core.module.connectorbusservice.grpc.*;
import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import com.orness.gandalf.core.module.subscribertopicmodule.domain.Subscriber;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@GRpcService
public class ConnectorBusGrpc extends ConnectorBusServiceGrpc.ConnectorBusServiceImplBase {

    @Autowired
    private ConnectorBusProducer connectorBusProducer;

    @Autowired
    private ConnectorBusManager connectorBusManager;

    public void sendMessage(MessageRequest request, StreamObserver<DefaultResponse> responseObserver) {
        System.out.println("Request sendMessage received from sample:\n" + request);

        MessageGandalf messageGandalf = new MessageGandalf(request.getMessage().getTopic(),
                request.getMessage().getSender(),
                request.getMessage().getExpirationTime(),
                request.getMessage().getCreationDate(),
                request.getMessage().getContent());
        connectorBusProducer.sendConnectorMessageKafka(request.getMessage().getTopic(), messageGandalf);

        DefaultResponse response = DefaultResponse.newBuilder().setMessage("Ok").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void createTopic(TopicRequest request, StreamObserver<DefaultResponse> responseObserver) {
        System.out.println("Request createTopic received from sample:\n" + request);

        connectorBusManager.topicCreation(request.getTopic().getName());

        DefaultResponse response = DefaultResponse.newBuilder().setMessage("Ok").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void deleteTopic(TopicRequest request, StreamObserver<DefaultResponse> responseObserver) {
        System.out.println("Request deleteTopic received from sample:\n" + request);

        connectorBusManager.topicSuppression(request.getTopic().getName());

        DefaultResponse response = DefaultResponse.newBuilder().setMessage("Ok").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void subscribeTopic(SubscribeRequest request, StreamObserver<DefaultResponse> responseObserver) {
        System.out.println("Request subscribeTopic received from sample:\n" + request);

        connectorBusManager.topicSubscription(request.getSubscribe().getTopic(), request.getSubscribe().getSubscriber());

        DefaultResponse response = DefaultResponse.newBuilder().setMessage("Ok").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void unsubscribeTopic(SubscribeRequest request, StreamObserver<DefaultResponse> responseObserver) {
        System.out.println("Request unsubscribeTopic received from sample:\n" + request);
        connectorBusManager.topicUnsubscription(request.getSubscribe().getTopic(), request.getSubscribe().getSubscriber());
    }

    public void getMessage(GetMessageRequest request, StreamObserver<MessageResponse> responseObserver) {
        System.out.println("Request getMessage received from sample:\n" + request);
        Subscriber subscriber = connectorBusManager.getSubscriberByNameInTopic(request.getMessage().getTopic(), request.getMessage().getSubscriber());
        //subscriber.startSubscriberZeroMQ();
        MessageGandalf messageGandalfBus = subscriber.getSubscriberZeroMQMessage();

        com.orness.gandalf.core.module.connectorbusservice.grpc.Message.Builder builder = com.orness.gandalf.core.module.connectorbusservice.grpc.Message.newBuilder();

        if(messageGandalfBus != null) {
            builder.setTopic(messageGandalfBus.getTopic())
                    .setSender(messageGandalfBus.getSender())
                    .setCreationDate(messageGandalfBus.getCreationDate().toString())
                    .setExpirationTime(messageGandalfBus.getExpirationTime().toString())
                    .setContent(messageGandalfBus.getContent());
        }
        com.orness.gandalf.core.module.connectorbusservice.grpc.Message message = builder.build();

        MessageResponse response = MessageResponse.newBuilder().setMessage(message).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void getMessageStream(GetMessageRequest request, StreamObserver<MessageResponse> responseObserver) {
        System.out.println("Request getMessageStream received from sample:\n" + request);

        boolean subscribe = true;
        MessageGandalf messageGandalfBus;
        Subscriber subscriber = connectorBusManager.getSubscriberByNameInTopic(request.getMessage().getTopic(), request.getMessage().getSubscriber());
        System.out.println("SUB SUB " + subscriber);
        while(subscribe) {
            System.out.println("ISSUB");
            if(subscriber != null) {
                System.out.println("NOT NULL");
                //subscriber.startSubscriberZeroMQ();
                System.out.println("TOTO");
                messageGandalfBus = subscriber.getSubscriberZeroMQMessage();
                System.out.println("MESSAGE " + messageGandalfBus);
                com.orness.gandalf.core.module.connectorbusservice.grpc.Message.Builder builder = com.orness.gandalf.core.module.connectorbusservice.grpc.Message.newBuilder();

                if(messageGandalfBus != null) {
                    System.out.println("MESS NOTNULL");
                    builder.setTopic(messageGandalfBus.getTopic())
                            .setSender(messageGandalfBus.getSender())
                            .setCreationDate(messageGandalfBus.getCreationDate().toString())
                            .setExpirationTime(messageGandalfBus.getExpirationTime().toString())
                            .setContent(messageGandalfBus.getContent());
                }
                com.orness.gandalf.core.module.connectorbusservice.grpc.Message message = builder.build();

                MessageResponse response = MessageResponse.newBuilder().setMessage(message).build();
                responseObserver.onNext(response);
            }
            subscribe = connectorBusManager.isSubscriberInTopic(request.getMessage().getTopic(), request.getMessage().getSubscriber());
            System.out.println("ISSUB " + subscribe);
        }
        responseObserver.onCompleted();
    }
}
