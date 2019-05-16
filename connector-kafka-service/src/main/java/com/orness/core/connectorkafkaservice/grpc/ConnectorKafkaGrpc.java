package com.orness.core.connectorkafkaservice.grpc;

import com.orness.core.connectorkafkaservice.domain.ConnectorKafkaServiceGrpc;
import com.orness.core.connectorkafkaservice.domain.MessageRequest;
import com.orness.core.connectorkafkaservice.domain.MessageResponse;
import com.orness.core.connectorkafkaservice.producer.ConnectorKafkaProducer;
import com.orness.core.messagekafkamodule.domain.MessageKafka;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class ConnectorKafkaGrpc extends ConnectorKafkaServiceGrpc.ConnectorKafkaServiceImplBase {

    @Autowired
    private ConnectorKafkaProducer connectorKafkaProducer;

    public void message(MessageRequest request, StreamObserver<MessageResponse> responseObserver) {
        System.out.println("Request received from client:\n" + request);

        String message = new StringBuilder()
                .append(request.getId())
                .append(request.getName())
                .append(request.getTopic())
                .append(request.getContent()).toString();

        System.out.println(message);

        MessageKafka messageKafka = new MessageKafka(request.getId(),request.getName(), request.getTopic(), request.getContent());
        connectorKafkaProducer.sendConnectorMessageKafka(request.getContent(), messageKafka);

        MessageResponse response = MessageResponse.newBuilder().setMessage(message).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
