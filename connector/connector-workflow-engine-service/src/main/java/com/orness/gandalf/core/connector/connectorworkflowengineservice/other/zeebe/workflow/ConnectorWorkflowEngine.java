/*
package com.orness.gandalf.core.connector.connectorworkflowengineservice.other.zeebe.workflow;

import com.orness.gandalf.core.module.messagemodule.gandalf.domain.GandalfMessage;
import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.orness.gandalf.core.module.constantmodule.workflow.WorkflowConstant.KEY_VARIABLE_WORKFLOW_MESSAGE;

@Component
public class ConnectorWorkflowEngine {

    private ZeebeClient zeebe;

    @Autowired
    public ConnectorWorkflowEngine(ZeebeClient zeebeClient) {
        this.zeebe = zeebeClient;
    }

    public void sendMessageWorkflowEngine(GandalfMessage gandalfMessage) {
        System.out.println("SEND WORKFLOW " + gandalfMessage);
        Map<String, String> variables = new HashMap<>();
        variables.put(KEY_VARIABLE_WORKFLOW_MESSAGE, gandalfMessage.getContent());
        zeebe.newPublishMessageCommand() //
                .messageName("message")
                .correlationKey(gandalfMessage.getTopic())
                .variables(variables)
                .timeToLive(Duration.ofMinutes(30))
                .send().join();
    }

}
*/
