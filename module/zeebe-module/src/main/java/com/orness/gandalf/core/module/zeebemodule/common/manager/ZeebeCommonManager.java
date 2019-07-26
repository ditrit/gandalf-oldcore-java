package com.orness.gandalf.core.module.zeebemodule.common.manager;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.messagemodule.gandalf.domain.GandalfEvent;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.events.DeploymentEvent;
import io.zeebe.client.api.events.WorkflowInstanceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
public class ZeebeCommonManager {

    private ZeebeClient zeebe;
    private Gson mapper;
    @Autowired
    public ZeebeCommonManager(ZeebeClient zeebeClient) {
        this.zeebe = zeebeClient;
        this.mapper = new Gson();
    }

    //TODO REVOIR CORRLATIONKEY
    public void sendMessageWorkflow(String message) {
        GandalfEvent gandalfEvent = mapper.fromJson(message, GandalfEvent.class);
        Map<String, String> variables = new HashMap<>();
        zeebe.newPublishMessageCommand() //
                .messageName("message")
                .correlationKey(gandalfEvent.getTopic())
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
