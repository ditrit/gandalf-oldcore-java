package com.orness.gandalf.core.module.customorchestratormodule.normative.manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.orness.gandalf.core.module.customorchestratormodule.core.ConnectorCustomOrchestratorBashService;
import com.orness.gandalf.core.module.orchestratormodule.manager.ConnectorOrchestratorNormativeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.orness.gandalf.core.module.customorchestratormodule.properties.ConnectorCustomOrchestratorConstant.SCRIPT_BUILD_DIRECTORY;

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
    public void register(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.connectorCustomOrchestratorBashService.register(jsonObject.get("service").getAsString(), jsonObject.get("version").getAsString());
    }

    @Override
    public void unregister(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.connectorCustomOrchestratorBashService.unregister(jsonObject.get("service").getAsString(), jsonObject.get("version").getAsString());
    }

    @Override
    public void deploy(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "deploy");
    }

    @Override
    public void undeploy(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "undeploy");
    }

    @Override
    public void start(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "start");
    }

    @Override
    public void stop(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "stop");
    }

    @Override
    public void scaleUp(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "scale_up");
    }

    @Override
    public void scaleDown(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "scale_down");
    }

    public void downloadProject(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.connectorCustomOrchestratorBashService.downloadProject(jsonObject.get("project_url").getAsString());
        this.connectorCustomOrchestratorBashService.downloadConfiguration(jsonObject.get("conf_url").getAsString());
    }
}
