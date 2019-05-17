package com.orness.core.connectorworkflowengineservice.grpc;

import com.orness.core.connectorworkflowengineservice.domain.ConnectorWorkflowEngineManager;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class ConnectorWorkflowEngineGrpc extends ConnectorWorkflowEngineServiceGrpc.ConnectorWorkflowEngineServiceImplBase {

    @Autowired
    private ConnectorWorkflowEngineManager connectorWorkflowEngineManager;

    public void subscribeTopic(SubscribeRequest request, StreamObserver<DefaultResponse> responseObserver) {
        Subscribe subscribe = request.getSubscribe();
        connectorWorkflowEngineManager.subscribeTopicBus(subscribe);
    }

    public void unsubscribeTopic(SubscribeRequest request, StreamObserver<DefaultResponse> responseObserver) {
        Subscribe subscribe = request.getSubscribe();
        connectorWorkflowEngineManager.unsubscribeTopicBus(subscribe);
    }
}
