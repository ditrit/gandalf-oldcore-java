package com.orness.core.testgrpcservice.entity;

import com.orness.namt.workflowtestgrpc.entity.MessageRequest;
import com.orness.namt.workflowtestgrpc.entity.MessageResponse;
import com.orness.namt.workflowtestgrpc.entity.WorkflowTestGrpcMessageServiceGrpc;
import io.grpc.stub.StreamObserver;

public class WorkflowTestOtherGRPCMessageImpl extends WorkflowTestGrpcMessageServiceGrpc.WorkflowTestGrpcMessageServiceImplBase {

    public void message(MessageRequest request, StreamObserver<MessageResponse> responseObserver) {
        System.out.println("Request received from client:\n" + request);

        String message = new StringBuilder().append(request.getMessage()).append("_server").toString();
        //TODO GESTION MESSAGE
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MessageResponse response = MessageResponse.newBuilder().setMessage(message).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}


