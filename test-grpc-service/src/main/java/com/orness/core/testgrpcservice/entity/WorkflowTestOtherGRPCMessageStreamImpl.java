package com.orness.core.testgrpcservice.entity;

import com.orness.namt.workflowtestgrpc.entity.MessageRequest;
import com.orness.namt.workflowtestgrpc.entity.MessageResponse;
import com.orness.namt.workflowtestgrpc.entity.WorkflowTestGrpcMessageServiceStreamGrpc;
import io.grpc.stub.StreamObserver;

public class WorkflowTestOtherGRPCMessageStreamImpl extends WorkflowTestGrpcMessageServiceStreamGrpc.WorkflowTestGrpcMessageServiceStreamImplBase {

    @Override
    public void message(MessageRequest request, StreamObserver<MessageResponse> responseObserver) {
        System.out.println("Request received from client:\n" + request);

        //TOTO_server_1
        String message = new StringBuilder().append(request.getMessage()).append("_server_1").toString();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MessageResponse response = MessageResponse.newBuilder().setMessage(message).build();
        responseObserver.onNext(response);

        //TOTO_server_2
        message = new StringBuilder().append(request.getMessage()).append("_server_2").toString();
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        response = MessageResponse.newBuilder().setMessage(message).build();
        responseObserver.onNext(response);

        //END
        responseObserver.onCompleted();
    }
}
