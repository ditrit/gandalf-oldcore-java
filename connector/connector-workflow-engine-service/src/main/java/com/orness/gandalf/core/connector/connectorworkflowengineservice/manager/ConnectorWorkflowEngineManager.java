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
        //ConnectorWorkflowEngineBusGrpc connectorWorkflowEngineBusGrpc = new ConnectorWorkflowEngineBusGrpc(this.connectorWorkflowEngine);
        //connectorWorkflowEngineBusGrpc.subscribeTopicDefault(subscribe.getTopic(), subscribe.getSubscriber());
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        grpcBusJavaClient.subscribeTopic(subscribe.getTopic(), subscribe.getSubscriber());
    }

    public Iterator<MessageResponse> getMessageStream(Subscribe subscribe) {
        //ConnectorWorkflowEngineBusGrpc connectorWorkflowEngineBusGrpc = new ConnectorWorkflowEngineBusGrpc(this.connectorWorkflowEngine);
        //connectorWorkflowEngineBusGrpc.subscribeTopic(subscribe.getTopic(), subscribe.getSubscriber());
        System.out.println("Sub One" + subscribe);

        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        Iterator<MessageResponse> messageResponseIterator = grpcBusJavaClient.getMessageStream(subscribe.getTopic(), subscribe.getSubscriber());
        /*MessageGandalf result = null;
        while(messageResponseIterator.hasNext()) {
            MessageGandalf currentMessage =  messageResponseIterator.next().getMessage();
            System.out.println("MessageGandalf " + currentMessage);

            result = new MessageGandalf(currentMessage.getTopic(),
                    currentMessage.getSender(),
                    currentMessage.getExpirationTime(),
                    currentMessage.getCreationDate(),
                    currentMessage.getContent());
            break;
        }*/
        return messageResponseIterator;
    }

    public void getMessageStreamWorkflow(Subscribe subscribe) {
        //ConnectorWorkflowEngineBusGrpc connectorWorkflowEngineBusGrpc = new ConnectorWorkflowEngineBusGrpc(this.connectorWorkflowEngine);
        //connectorWorkflowEngineBusGrpc.subscribeTopic(subscribe.getTopic(), subscribe.getSubscriber());
        System.out.println("Sub " + subscribe);
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        Iterator<MessageResponse> messageResponseIterator = grpcBusJavaClient.getMessageStream(subscribe.getTopic(), subscribe.getSubscriber());
        while(messageResponseIterator.hasNext()) {
            com.orness.gandalf.core.module.connectorbusservice.grpc.Message currentMessage =  messageResponseIterator.next().getMessage();
            System.out.println("MessageGandalf " + currentMessage);

            this.connectorWorkflowEngine.sendMessageWorkflowEngine(new MessageGandalf(currentMessage.getTopic(),
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
