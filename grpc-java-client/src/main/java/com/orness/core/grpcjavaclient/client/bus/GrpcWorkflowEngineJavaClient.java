package com.orness.core.grpcjavaclient.client.bus;

import com.orness.core.connectorbusservice.grpc.ConnectorBusServiceGrpc;
import com.orness.core.connectorbusservice.grpc.GetMessage;
import com.orness.core.connectorbusservice.grpc.GetMessageRequest;
import com.orness.core.connectorworkflowengineservice.grpc.ConnectorWorkflowEngineServiceGrpc;
import com.orness.core.connectorworkflowengineservice.grpc.Subscribe;
import com.orness.core.connectorworkflowengineservice.grpc.SubscribeRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcWorkflowEngineJavaClient {

    private static ManagedChannel channelWE;
    private static ManagedChannel channelBUS;
    private static ConnectorWorkflowEngineServiceGrpc.ConnectorWorkflowEngineServiceBlockingStub stubWE;
    private static ConnectorBusServiceGrpc.ConnectorBusServiceBlockingStub stubBus;

    public static void main(String[] args) {
        StartClient();
        SubscribeTopic();
System.out.println("Subs");
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
System.out.println("GET");
        getMessage();
    }

    public static void StartClient() {
        channelWE = ManagedChannelBuilder.forAddress("localhost", 10002)
                .usePlaintext()
                .build();
        channelBUS = ManagedChannelBuilder.forAddress("localhost", 10000)
                .usePlaintext()
                .build();

        stubWE = ConnectorWorkflowEngineServiceGrpc.newBlockingStub(channelWE);
        stubBus = ConnectorBusServiceGrpc.newBlockingStub(channelBUS);
    }

    private static void StopClient() {
        channelWE.shutdown();
    }

    private static void SubscribeTopic() {
        Subscribe.Builder builder = Subscribe.newBuilder();
        builder.setId("toto")
                .setListTopics("toto")
                .setName("toto")
                .setSeparator(";");
        Subscribe subscribe = builder.build();

        SubscribeRequest request = SubscribeRequest.newBuilder().setSubscribe(subscribe).build();
        System.out.println(stubWE.subscribeTopic(request));
    }


    //TODO REVOIR PROTO FILE
    private static void UnsubscribeTopic() {

    }

    private static void getMessage() {
        //GETMESSAGEREQUEST -> MESSAGEREQUEST
        GetMessage.Builder builder = GetMessage.newBuilder();
        builder.setIdSender("toto")
                .setName("toto")
                .setTopic("toto");
        GetMessage getMessage = builder.build();

        GetMessageRequest request = GetMessageRequest.newBuilder().setMessage(getMessage).build();
        System.out.println(stubBus.getMessage(request));

    }

}
