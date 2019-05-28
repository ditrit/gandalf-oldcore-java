package com.orness.gandalf.core.connector.connectorworkflowengineservice.manager;

import com.orness.gandalf.core.connector.connectorworkflowengineservice.workflow.ConnectorWorkflowEngine;
import com.orness.gandalf.core.module.connectorbusservice.grpc.Message;
import com.orness.gandalf.core.module.connectorbusservice.grpc.MessageResponse;
import com.orness.gandalf.core.module.connectorworkflowengineservice.grpc.Subscribe;
import com.orness.gandalf.core.library.grpcjavaclient.bus.GrpcBusJavaClient;
import com.orness.gandalf.core.module.messagebusmodule.domain.MessageBus;
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
        //ConnectorWorkflowEngineBusGrpc connectorWorkflowEngineBusGrpc = new ConnectorWorkflowEngineBusGrpc(this.connectorWorkflowEngine);
        //connectorWorkflowEngineBusGrpc.subscribeTopicDefault(subscribe.getTopic(), subscribe.getSubscriber());
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        grpcBusJavaClient.subscribeTopic(subscribe.getTopic(), subscribe.getSubscriber());
    }

    public MessageBus getOneMessageStream(Subscribe subscribe) {
        //ConnectorWorkflowEngineBusGrpc connectorWorkflowEngineBusGrpc = new ConnectorWorkflowEngineBusGrpc(this.connectorWorkflowEngine);
        //connectorWorkflowEngineBusGrpc.subscribeTopic(subscribe.getTopic(), subscribe.getSubscriber());
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        Iterator<MessageResponse> messageResponseIterator = grpcBusJavaClient.getMessageStream(subscribe.getTopic(), subscribe.getSubscriber());
        Message currentMessage =  messageResponseIterator.next().getMessage();
        return new MessageBus(currentMessage.getTopic(),
                    currentMessage.getSender(),
                    currentMessage.getExpirationTime(),
                    currentMessage.getCreationDate(),
                    currentMessage.getContent());
    }

    public void getMessageStream(Subscribe subscribe) {
        //ConnectorWorkflowEngineBusGrpc connectorWorkflowEngineBusGrpc = new ConnectorWorkflowEngineBusGrpc(this.connectorWorkflowEngine);
        //connectorWorkflowEngineBusGrpc.subscribeTopic(subscribe.getTopic(), subscribe.getSubscriber());
        System.out.println("Sub " + subscribe);
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        Iterator<MessageResponse> messageResponseIterator = grpcBusJavaClient.getMessageStream(subscribe.getTopic(), subscribe.getSubscriber());
        System.out.println("Next " + messageResponseIterator.hasNext());
        while(messageResponseIterator.hasNext()) {
            Message currentMessage =  messageResponseIterator.next().getMessage();
            System.out.println("Message " + currentMessage);

            this.connectorWorkflowEngine.sendMessageWorkflowEngine(new MessageBus(currentMessage.getTopic(),
                    currentMessage.getSender(),
                    currentMessage.getExpirationTime(),
                    currentMessage.getCreationDate(),
                    currentMessage.getContent()));
        }
    }

    public void unsubscribeTopicBus(Subscribe subscribe) {
        //ConnectorWorkflowEngineBusGrpc connectorWorkflowEngineBusGrpc = new ConnectorWorkflowEngineBusGrpc(this.connectorWorkflowEngine);
        //connectorWorkflowEngineBusGrpc.unsubscribeTopic(subscribe.getTopic(), subscribe.getSubscriber());
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        grpcBusJavaClient.unsubscribeTopic(subscribe.getTopic(), subscribe.getSubscriber());
    }

}
