package com.orness.core.testgrpcservice.client;

import com.orness.namt.workflowtestgrpc.entity.MessageRequest;
import com.orness.namt.workflowtestgrpc.entity.MessageResponse;
import com.orness.namt.workflowtestgrpc.entity.WorkflowTestGrpcMessageServiceGrpc;
import com.orness.namt.workflowtestgrpc.entity.WorkflowTestGrpcMessageServiceStreamGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.IOException;
import java.util.Iterator;

public class GRPCClient {

    private static  ManagedChannel channel;
    private static WorkflowTestGrpcMessageServiceStreamGrpc.WorkflowTestGrpcMessageServiceStreamBlockingStub stub;

    public static void main(String[] args) {
        StartClient();
        MessageClient("TOTO");
    }

    public static void StartClient() {
        channel = ManagedChannelBuilder.forAddress("localhost", 9005)
                .usePlaintext()
                .build();

        stub = WorkflowTestGrpcMessageServiceStreamGrpc.newBlockingStub(channel);
    }

    private static void StopClient() {
        channel.shutdown();
    }


    private static void MessageClient(String message) {
        Iterator<MessageResponse> messageResponseIterator;
        //REQUEST
        MessageRequest messageRequest = MessageRequest.newBuilder().setMessage(message).build();
        //CALL RPC
        messageResponseIterator = stub.message(messageRequest);
        //PRINT
        while (messageResponseIterator.hasNext()) {
            System.out.println("Response received from server:\n" + messageResponseIterator.next());

        }
    }
}
