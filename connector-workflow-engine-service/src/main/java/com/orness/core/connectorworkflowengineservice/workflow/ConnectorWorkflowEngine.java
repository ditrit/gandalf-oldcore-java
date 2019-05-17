package com.orness.core.connectorworkflowengineservice.workflow;

import com.orness.core.messageworkflowenginemodule.domain.MessageWorkflowEngine;
import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class ConnectorWorkflowEngine {

    private ZeebeClient zeebe;

    @Autowired
    public ConnectorWorkflowEngine(ZeebeClient zeebeClient) {
        this.zeebe = zeebeClient;
    }

    public void sendMessageWorkflowEngine(MessageWorkflowEngine messageWorkflowEngine) {

        zeebe.newPublishMessageCommand() //
                .messageName(messageWorkflowEngine.getWorkflow_name())
                .correlationKey(messageWorkflowEngine.getWorkflow_topic())
                .variables(messageWorkflowEngine.getWorkflow_content())
                .timeToLive(Duration.ofMinutes(30))
                .send().join();
    }
}
