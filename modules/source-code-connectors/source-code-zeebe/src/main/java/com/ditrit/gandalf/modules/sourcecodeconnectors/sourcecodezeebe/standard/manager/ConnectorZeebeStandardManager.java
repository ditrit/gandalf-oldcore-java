package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.standard.manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ditrit.gandalf.modules.abstractconnectors.abstractworkflowengine.manager.ConnectorWorkflowEngineStandardManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.core.domain.ConnectorZeebeMessage;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.properties.ConnectorZeebeProperties;
import com.ditrit.gandalf.core.zeromqcore.event.domain.MessageEvent;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.events.DeploymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;

@Component(value = "standardManager")
@ConditionalOnBean(ConnectorZeebeProperties.class)
public class ConnectorZeebeStandardManager extends ConnectorWorkflowEngineStandardManager {

    private ZeebeClient zeebe;
    private Gson mapper;

    @Autowired
    public ConnectorZeebeStandardManager(ZeebeClient zeebeClient) {
        this.zeebe = zeebeClient;
        this.mapper = new Gson();
    }

    @Override
    public String deployWorkflow(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        DeploymentEvent deploymentEvent = zeebe.newDeployCommand()
                .addResourceFromClasspath(jsonObject.get("workflow").getAsString())
                .send().join();
        return this.getIdDeployment(deploymentEvent);
    }

    @Override
    public void instanciateWorkflow(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
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
        System.out.println("HOOK MANAGER");
        JsonObject jsonObject = this.mapper.fromJson(messageEvent.getPayload(), JsonObject.class);
        System.out.println(jsonObject.get("project_url").getAsString());
        HashMap<String, String> variables = new HashMap<>();
        variables.put("project_name", jsonObject.get("project_name").getAsString());
        variables.put("project_url", jsonObject.get("project_url").getAsString());
        //variables.put(" project_version", this.jsonObject.get("project_version").getAsString());
        System.out.println("VARIRABLES");
        System.out.println(variables.toString());
        System.out.println(messageEvent.getTopic());
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
