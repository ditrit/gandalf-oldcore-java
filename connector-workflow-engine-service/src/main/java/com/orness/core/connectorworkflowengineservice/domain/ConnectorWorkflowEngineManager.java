package com.orness.core.connectorworkflowengineservice.domain;

import com.orness.core.connectorworkflowengineservice.grpc.ConnectorWorkflowEngineBusGrpc;
import com.orness.core.connectorworkflowengineservice.grpc.Subscribe;
import com.orness.core.connectorworkflowengineservice.workflow.ConnectorWorkflowEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectorWorkflowEngineManager {

    private ConnectorWorkflowEngine connectorWorkflowEngine;

    @Autowired
    public ConnectorWorkflowEngineManager(ConnectorWorkflowEngine connectorWorkflowEngine) {
        this.connectorWorkflowEngine = connectorWorkflowEngine;
    }

    public void subscribeTopicBus(Subscribe subscribe) {
        for(String topic : subscribe.getListTopics().split(subscribe.getSeparator())) {
            System.out.println(topic);
            ConnectorWorkflowEngineBusGrpc connectorWorkflowEngineBusGrpc = new ConnectorWorkflowEngineBusGrpc(this.connectorWorkflowEngine);
            connectorWorkflowEngineBusGrpc.subscribeTopic(subscribe.getId(), subscribe.getName(), topic);
        }
    }

    public void unsubscribeTopicBus(Subscribe subscribe) {
        for(String topic : subscribe.getListTopics().split(subscribe.getSeparator())) {
            ConnectorWorkflowEngineBusGrpc connectorWorkflowEngineBusGrpc = new ConnectorWorkflowEngineBusGrpc(this.connectorWorkflowEngine);
            connectorWorkflowEngineBusGrpc.unsubscribeTopic(subscribe.getId(), subscribe.getName(), topic);
        }

    }

}
