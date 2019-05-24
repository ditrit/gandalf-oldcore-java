package com.orness.gandalf.core.connectorbusservice.grpc;

import com.orness.gandalf.core.connectorbusservice.manager.ConnectorBusManager;
import com.orness.gandalf.core.connectorbusservice.producer.ConnectorBusProducer;
import com.orness.gandalf.core.messagebusmodule.domain.MessageBus;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class ConnectorBusGrpc extends ConnectorBusServiceGrpc.ConnectorBusServiceImplBase {

    @Autowired
    private ConnectorBusProducer connectorBusProducer;

    @Autowired
    private ConnectorBusManager connectorBusManager;

    public void sendMessage(MessageRequest request, StreamObserver<DefaultResponse> responseObserver) {
        System.out.println("Request sendMessage received from sample:\n" + request);

        MessageBus messageBus = new MessageBus(request.getMessage().getTopic(),
                request.getMessage().getSender(),
                request.getMessage().getExpirationTime(),
                request.getMessage().getCreationDate(),
                request.getMessage().getContent());
        connectorBusProducer.sendConnectorMessageKafka(request.getMessage().getTopic(), messageBus);

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
        MessageBus messageBus = connectorBusManager.getMessageTopicBySubscriber(request.getMessage().getTopic(), request.getMessage().getSubscriber());

        Message message = null;
        if(messageBus != null) {
            Message.Builder builder = Message.newBuilder();
            builder.setTopic(messageBus.getTopic())
                    .setSender(messageBus.getSender())
                    .setCreationDate(messageBus.getCreationDate().toString())
                    .setExpirationTime(messageBus.getExpirationTime().toString())
                    .setContent(messageBus.getContent());

            message = builder.build();
        }

        MessageResponse response = MessageResponse.newBuilder().setMessage(message).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void getMessageStream(GetMessageRequest request, StreamObserver<MessageResponse> responseObserver) {
        System.out.println("Request getMessageStream received from sample:\n" + request);

        boolean subscribe = true;
        MessageBus messageBus;
        while(subscribe) {

            while(connectorBusManager.isSubscriberIndexValid(request.getMessage().getTopic(), request.getMessage().getSubscriber())) {
                messageBus = connectorBusManager.getMessageTopicBySubscriber(request.getMessage().getTopic(), request.getMessage().getSubscriber());

                Message.Builder builder = Message.newBuilder();
                builder.setTopic(messageBus.getTopic())
                        .setSender(messageBus.getSender())
                        .setCreationDate(messageBus.getCreationDate().toString())
                        .setExpirationTime(messageBus.getExpirationTime().toString())
                        .setContent(messageBus.getContent());

                Message message = builder.build();

                MessageResponse response = MessageResponse.newBuilder().setMessage(message).build();
                responseObserver.onNext(response);
            }

            subscribe = connectorBusManager.isSubscriberInTopic(request.getMessage().getTopic(), request.getMessage().getSubscriber());
        }
        responseObserver.onCompleted();
    }
}
