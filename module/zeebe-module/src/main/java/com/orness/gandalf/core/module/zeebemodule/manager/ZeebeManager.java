package com.orness.gandalf.core.module.zeebemodule.manager;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.events.DeploymentEvent;
import io.zeebe.client.api.events.WorkflowInstanceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
public class ZeebeManager {

    private ZeebeClient zeebe;

    @Autowired
    public ZeebeManager(ZeebeClient zeebeClient) {
        this.zeebe = zeebeClient;
    }

    //TODO REVOIR
    public void sendMessageWorkflow(String message) {
        System.out.println("SEND WORKFLOW " + message);
        Map<String, String> variables = new HashMap<>();
        //variables.put(KEY_VARIABLE_WORKFLOW_MESSAGE, messageGandalf.getContent());
        zeebe.newPublishMessageCommand() //
                .messageName("message")
                .correlationKey(messageGandalf.getTopic())
                .variables(variables)
                .timeToLive(Duration.ofMinutes(30))
                .send().join();
    }

    public String deployWorkflow(String workflow_name) {
        DeploymentEvent deploymentEvent = zeebe.newDeployCommand()
                .addResourceFromClasspath(workflow_name)
                .send().join();
        return this.getIdDeployment(deploymentEvent);
    }

    public WorkflowInstanceEvent instanciateWorkflow(String workflow_id, HashMap<String, String> workflow_variables) {
        return zeebe.newCreateInstanceCommand()
                .bpmnProcessId(workflow_id)
                .latestVersion()
                .variables(workflow_variables)
                .send().join();
    }

    private String getIdDeployment(DeploymentEvent deploymentEvent) {
        return deploymentEvent.getWorkflows().get(0).getBpmnProcessId();
    }
}
