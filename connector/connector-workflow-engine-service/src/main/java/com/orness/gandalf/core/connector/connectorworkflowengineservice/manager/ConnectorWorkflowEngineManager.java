package com.orness.gandalf.core.connector.connectorworkflowengineservice.manager;

import com.orness.gandalf.core.connector.connectorworkflowengineservice.workflow.ConnectorWorkflowEngine;
import com.orness.gandalf.core.module.connectorbusservice.grpc.MessageResponse;
import com.orness.gandalf.core.module.connectorworkflowengineservice.grpc.Subscribe;
import com.orness.gandalf.core.library.grpcjavaclient.bus.GrpcBusJavaClient;
import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class ConnectorWorkflowEngineManager {

    private ConnectorWorkflowEngine connectorWorkflowEngine;

    @Autowired
    public ConnectorWorkflowEngineManager(ConnectorWorkflowEngine connectorWorkflowEngine) {
        this.connectorWorkflowEngine = connectorWorkflowEngine;
    }

    public void subscribeTopicBus(Subscribe subscribe) {
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        grpcBusJavaClient.subscribeTopic(subscribe.getTopic(), subscribe.getSubscriber());
        grpcBusJavaClient.stopClient();
    }

    public Iterator<MessageResponse> getMessageStream(Subscribe subscribe) {
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        Iterator<MessageResponse> messageResponseIterator = grpcBusJavaClient.getMessageStream(subscribe.getTopic(), subscribe.getSubscriber());

        grpcBusJavaClient.stopClient();
        return messageResponseIterator;
    }

    public void getMessageStreamWorkflow(Subscribe subscribe) {
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        Iterator<MessageResponse> messageResponseIterator = grpcBusJavaClient.getMessageStream(subscribe.getTopic(), subscribe.getSubscriber());
        while(messageResponseIterator.hasNext()) {
            com.orness.gandalf.core.module.connectorbusservice.grpc.Message currentMessage =  messageResponseIterator.next().getMessage();

            this.connectorWorkflowEngine.sendMessageWorkflowEngine(new MessageGandalf(currentMessage.getTopic(),
                    currentMessage.getSender(),
                    currentMessage.getExpirationTime(),
                    currentMessage.getCreationDate(),
                    currentMessage.getContent()));
        }
        grpcBusJavaClient.stopClient();
    }

    public void unsubscribeTopicBus(Subscribe subscribe) {
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        grpcBusJavaClient.unsubscribeTopic(subscribe.getTopic(), subscribe.getSubscriber());
        grpcBusJavaClient.stopClient();
    }

}
