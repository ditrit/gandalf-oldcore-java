package com.orness.gandalf.core.module.zeebemodule.normative.manager;

import com.orness.gandalf.core.module.workflowenginemodule.manager.ConnectorWorkflowEngineNormativeManager;
import com.orness.gandalf.core.module.zeebemodule.core.domain.ConnectorZeebeMessage;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.events.DeploymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component(value = "commonManager")
@Profile(value = "zeebe-module")
public class ConnectorZeebeNormativeManager extends ConnectorWorkflowEngineNormativeManager {

    private ZeebeClient zeebe;

    @Autowired
    public ConnectorZeebeNormativeManager(ZeebeClient zeebeClient) {
        this.zeebe = zeebeClient;
    }

    @Override
    public String deployWorkflow(String workflow) {
        DeploymentEvent deploymentEvent = zeebe.newDeployCommand()
                .addResourceFromClasspath(workflow)
                .send().join();
        return this.getIdDeployment(deploymentEvent);
    }

    @Override
    public void instanciateWorkflow(String id, Object variables) {
        zeebe.newCreateInstanceCommand()
                .bpmnProcessId(id)
                .latestVersion()
                .variables(variables)
                .send().join();
    }

    @Override
    public void sendMessage(Object message) {
        ConnectorZeebeMessage connectorZeebeMessage = ((ConnectorZeebeMessage) message);
        zeebe.newPublishMessageCommand() //
                .messageName(connectorZeebeMessage.getName())
                .correlationKey(connectorZeebeMessage.getCorrelationKey())
                .variables(connectorZeebeMessage.getVariables())
                .timeToLive(Duration.ofMinutes(connectorZeebeMessage.getDuration()))
                .send().join();
    }

    private String getIdDeployment(DeploymentEvent deploymentEvent) {
        return deploymentEvent.getWorkflows().get(0).getBpmnProcessId();
    }
}
