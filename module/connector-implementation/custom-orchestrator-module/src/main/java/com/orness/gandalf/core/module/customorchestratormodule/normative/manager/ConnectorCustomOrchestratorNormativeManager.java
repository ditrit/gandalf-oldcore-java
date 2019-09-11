package com.orness.gandalf.core.module.customorchestratormodule.normative.manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.orness.gandalf.core.module.customorchestratormodule.core.ConnectorCustomOrchestratorBashService;
import com.orness.gandalf.core.module.orchestratormodule.manager.ConnectorOrchestratorNormativeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "normativeManager")
@Profile(value = "custom-orchestrator")
public class ConnectorCustomOrchestratorNormativeManager extends ConnectorOrchestratorNormativeManager {

    private ConnectorCustomOrchestratorBashService connectorCustomOrchestratorBashService;
    private Gson mapper;

    @Autowired
    public ConnectorCustomOrchestratorNormativeManager(ConnectorCustomOrchestratorBashService connectorCustomOrchestratorBashService) {
        this.connectorCustomOrchestratorBashService = connectorCustomOrchestratorBashService;
        this.mapper = new Gson();
    }

    @Override
    public boolean register(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        return this.connectorCustomOrchestratorBashService.register(jsonObject.get("service").getAsString(), jsonObject.get("version").getAsString());
    }

    @Override
    public boolean unregister(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        return this.connectorCustomOrchestratorBashService.unregister(jsonObject.get("service").getAsString(), jsonObject.get("version").getAsString());
    }

    @Override
    public boolean deploy(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        return this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "deploy");
    }

    @Override
    public boolean undeploy(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        return this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "undeploy");
    }

    @Override
    public boolean start(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        return this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "start");
    }

    @Override
    public boolean stop(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        return this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "stop");
    }

    @Override
    public boolean scaleUp(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        return this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "scale_up");
    }

    @Override
    public boolean scaleDown(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        return this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "scale_down");
    }

    public boolean downloadProject(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        boolean result = true;
        result &= this.connectorCustomOrchestratorBashService.downloadProject(jsonObject.get("project_url").getAsString());
        result &= this.connectorCustomOrchestratorBashService.downloadConfiguration(jsonObject.get("conf_url").getAsString());
        return result;
    }
}
