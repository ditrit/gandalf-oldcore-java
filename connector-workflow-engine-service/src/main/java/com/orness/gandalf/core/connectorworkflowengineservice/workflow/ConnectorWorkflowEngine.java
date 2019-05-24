package com.orness.gandalf.core.connectorworkflowengineservice.workflow;

import com.orness.gandalf.core.messagebusmodule.domain.MessageBus;
import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void sendMessageWorkflowEngine(MessageBus messageBus) {

        System.out.println("ZEEBE MESSAGE " + messageBus);

        Map<String, String> test_variables = new HashMap<>();
        test_variables.put("content", messageBus.getContent());

        zeebe.newPublishMessageCommand() //
                .messageName(messageBus.getTopic())
                .correlationKey(messageBus.getTopic())
                .variables(test_variables)
                .timeToLive(Duration.ofMinutes(30))
                .send().join();
    }
}
