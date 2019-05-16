package com.orness.core.testgrpcservice.server;

import com.orness.core.testgrpcservice.entity.WorkflowTestOtherGRPCMessageImpl;
import com.orness.core.testgrpcservice.entity.WorkflowTestOtherGRPCMessageStreamImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GRPCServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(9005)
                .addService(new WorkflowTestOtherGRPCMessageImpl())
                .addService(new WorkflowTestOtherGRPCMessageStreamImpl())
                .build();

        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started!");
        server.awaitTermination();
    }
}
