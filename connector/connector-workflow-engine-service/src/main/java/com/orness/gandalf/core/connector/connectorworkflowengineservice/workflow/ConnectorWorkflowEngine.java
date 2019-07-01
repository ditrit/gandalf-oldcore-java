package com.orness.gandalf.core.connector.connectorworkflowengineservice.workflow;

import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
public class ConnectorWorkflowEngine {

    private ZeebeClient zeebe;

    @Autowired
    public ConnectorWorkflowEngine(ZeebeClient zeebeClient) {
        this.zeebe = zeebeClient;
    }

    public void sendMessageWorkflowEngine(MessageGandalf messageGandalf) {
        System.out.println("SEND WORKFLOW " + messageGandalf);
        Map<String, String> variables = new HashMap<>();
        variables.put("content", messageGandalf.getContent());
        zeebe.newPublishMessageCommand() //
                .messageName("message")
                .correlationKey(messageGandalf.getTopic())
                .variables(variables)
                .timeToLive(Duration.ofMinutes(30))
                .send().join();
    }

}
