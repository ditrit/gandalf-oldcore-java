package com.orness.core.connectorbusservice.grpc;

import com.orness.core.connectorbusservice.domain.ConnectorBusManager;
import com.orness.core.connectorbusservice.producer.ConnectorBusProducer;
import com.orness.core.messagebusmodule.domain.MessageBus;
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
        System.out.println("Request received from client:\n" + request);

        String message = new StringBuilder()
                .append(request.getMessage().getIdSender())
                .append(request.getMessage().getName())
                .append(request.getMessage().getTopic())
                .append(request.getMessage().getCreationDate())
                .append(request.getMessage().getExpirationTime())
                .append(request.getMessage().getContent()).toString();
        System.out.println(message);

        MessageBus messageBus = new MessageBus(request.getMessage().getIdSender(),
                request.getMessage().getName(),
                request.getMessage().getTopic(),
                request.getMessage().getCreationDate(),
                request.getMessage().getExpirationTime(),
                request.getMessage().getContent());
        connectorBusProducer.sendConnectorMessageKafka(request.getMessage().getTopic(), messageBus);

        DefaultResponse response = DefaultResponse.newBuilder().setMessage(message).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void sendMessageStream(MessageRequest request, StreamObserver<MessageResponse> responseObserver) {
        //USELESS
    }

    public void createTopic(TopicRequest request, StreamObserver<DefaultResponse> responseObserver) {
        System.out.println("Request received from client:\n" + request);

        String message = new StringBuilder()
                .append(request.getTopic().getName())
                .append(request.getTopic().getNumPartitions())
                .append(request.getTopic().getReplicationFactor())
                .toString();
        System.out.println(message);

        connectorBusManager.topicCreation(request.getTopic().getName());

        DefaultResponse response = DefaultResponse.newBuilder().setMessage(message).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void deleteTopic(TopicRequest request, StreamObserver<DefaultResponse> responseObserver) {
        System.out.println("Request received from client:\n" + request);

        String message = new StringBuilder()
                .append(request.getTopic().getName())
                .append(request.getTopic().getNumPartitions())
                .append(request.getTopic().getReplicationFactor())
                .toString();
        System.out.println(message);

        connectorBusManager.topicSuppression(request.getTopic().getName());

        DefaultResponse response = DefaultResponse.newBuilder().setMessage(message).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void subscribeTopic(SubscribeRequest request, StreamObserver<MessageResponse> responseObserver) {
        System.out.println("Request received from client:\n" + request);
        connectorBusManager.topicSubscription(request.getSubscribe().getName(), request.getSubscribe().getTopic());
    }

    public void unsubscribeTopic(SubscribeRequest request, StreamObserver<DefaultResponse> responseObserver) {
        System.out.println("Request received from client:\n" + request);
        connectorBusManager.topicUnsubscription(request.getSubscribe().getName(), request.getSubscribe().getTopic());
    }

    public void getMessage(GetMessageRequest request, StreamObserver<MessageResponse> responseObserver) {
        System.out.println("Request received from client:\n" + request);
        MessageBus messageBus = connectorBusManager.getMessageTopicByWorkflow(request.getMessage().getTopic(), request.getMessage().getIdSender());

        Message.Builder builder = Message.newBuilder();
        builder.setIdSender(messageBus.getWorkflow_id_sender())
                .setName(messageBus.getWorkflow_name())
                .setTopic(messageBus.getWorkflow_topic())
                .setCreationDate(messageBus.getWorkflow_creation_date().toString())
                .setExpirationTime(messageBus.getWorkflow_expiration_time().toString())
                .setContent(messageBus.getWorkflow_content());

        Message message = builder.build();

        MessageResponse response = MessageResponse.newBuilder().setMessage(message).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
