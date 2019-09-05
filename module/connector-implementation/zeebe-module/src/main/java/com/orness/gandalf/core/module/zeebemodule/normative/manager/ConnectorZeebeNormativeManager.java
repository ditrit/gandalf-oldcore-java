package com.orness.gandalf.core.module.zeebemodule.normative.manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.orness.gandalf.core.module.workflowenginemodule.manager.ConnectorWorkflowEngineNormativeManager;
import com.orness.gandalf.core.module.zeebemodule.core.domain.ConnectorZeebeMessage;
import com.orness.gandalf.core.module.zeromqcore.event.domain.MessageEvent;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.events.DeploymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;

@Component(value = "normativeManager")
@Profile(value = "zeebe")
public class ConnectorZeebeNormativeManager extends ConnectorWorkflowEngineNormativeManager {

    private ZeebeClient zeebe;
    private Gson mapper;
    private JsonObject jsonObject;

    @Autowired
    public ConnectorZeebeNormativeManager(ZeebeClient zeebeClient) {
        this.zeebe = zeebeClient;
        this.mapper = new Gson();
    }

    @Override
    public String deployWorkflow(String payload) {
        this.jsonObject = mapper.fromJson(payload, JsonObject.class);
        DeploymentEvent deploymentEvent = zeebe.newDeployCommand()
                .addResourceFromClasspath(jsonObject.get("workflow").getAsString())
                .send().join();
        return this.getIdDeployment(deploymentEvent);
    }

    @Override
    public void instanciateWorkflow(String payload) {
        this.jsonObject = mapper.fromJson(payload, JsonObject.class);
        zeebe.newCreateInstanceCommand()
                .bpmnProcessId(jsonObject.get("id").getAsString())
                .latestVersion()
                .variables(jsonObject.get("variables").getAsString())
                .send().join();
    }

    @Override
    public void sendMessage(String payload) {
        //TODO REVOIR
        ConnectorZeebeMessage connectorZeebeMessage = this.mapper.fromJson(payload, ConnectorZeebeMessage.class);
        zeebe.newPublishMessageCommand() //
                .messageName(connectorZeebeMessage.getName())
                .correlationKey(connectorZeebeMessage.getCorrelationKey())
                .variables(connectorZeebeMessage.getVariables())
                .timeToLive(Duration.ofMinutes(connectorZeebeMessage.getDuration()))
                .send().join();
    }

    public void hookMerge(MessageEvent messageEvent) {
        this.jsonObject = this.mapper.fromJson(messageEvent.getPayload(), JsonObject.class);
        HashMap<String, String> variables = new HashMap<>();
        variables.put(" project_url", this.jsonObject.get("project_url").getAsString());
        variables.put("project_name", this.jsonObject.get("project_name").getAsString());
        variables.put(" project_version", this.jsonObject.get("project_version").getAsString());

        zeebe.newPublishMessageCommand() //
                .messageName("message")
                .correlationKey(messageEvent.getTopic())
                .variables(variables)
                .timeToLive(Duration.ofMinutes(100L))
                .send().join();
    }

    private String getIdDeployment(DeploymentEvent deploymentEvent) {
        return deploymentEvent.getWorkflows().get(0).getBpmnProcessId();
    }
}
