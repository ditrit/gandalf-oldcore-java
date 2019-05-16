package com.orness.core.testgrpcservice.client;


import com.orness.core.testgrpcservice.entity.MessageFuture;
import com.orness.namt.workflowtestgrpc.entity.MessageRequest;
import com.orness.namt.workflowtestgrpc.entity.MessageResponse;
import com.orness.namt.workflowtestgrpc.entity.WorkflowTestGrpcMessageServiceGrpc;
import com.orness.namt.workflowtestgrpc.entity.WorkflowTestGrpcMessageServiceGrpc.WorkflowTestGrpcMessageServiceFutureStub;
import com.orness.namt.workflowtestgrpc.entity.WorkflowTestGrpcMessageServiceGrpc.WorkflowTestGrpcMessageServiceStub;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AsyncClient {


    private final ManagedChannel channel;
    //private final WorkflowTestGrpcMessageServiceStub asyncStub;

    /** Construct client for accessing RouteGuide server at {@code host:port}. */
    public AsyncClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
    }

    /** Construct client for accessing RouteGuide server using the existing channel. */
    public AsyncClient(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        //asyncStub = WorkflowTestGrpcMessageServiceGrpc.newFutureStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9005)
                .usePlaintext()
                .build();
        MessageRequest message = MessageRequest.newBuilder().setMessage("TOTO").build();
        Future<String> future = doUnaryCalls(channel,message, 10);

        while(!future.isDone()) {
            System.out.println("Calculating...");
            Thread.sleep(300);
        }

        try {
            System.out.println("FUTURE " + future.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static Future<String> doUnaryCalls(Channel channel, final MessageRequest request, final long endTime) {
    //final WorkflowTestGrpcMessageServiceFutureStub stub = WorkflowTestGrpcMessageServiceGrpc.newFutureStub(channel);
    final WorkflowTestGrpcMessageServiceStub stub = WorkflowTestGrpcMessageServiceGrpc.newStub(channel);
    final String message = "toto";
    final MessageFuture future = new MessageFuture(message);

        stub.message(request, new StreamObserver<MessageResponse>() {
            long lastCall = System.nanoTime();

            @Override
            public void onNext(MessageResponse value) {
            }

            @Override
            public void onError(Throwable t) {
                Status status = Status.fromThrowable(t);
                System.err.println("Encountered an error in unaryCall. Status is " + status);
                t.printStackTrace();

                future.cancel(true);
            }

            @Override
            public void onCompleted() {
                long now = System.nanoTime();
                // Record the latencies in microseconds
                lastCall = now;

                if (endTime - now > 0) {
                    stub.message(request, this);
                } else {
                    future.done();
                }
            }
        });

        return future;
    }
}
