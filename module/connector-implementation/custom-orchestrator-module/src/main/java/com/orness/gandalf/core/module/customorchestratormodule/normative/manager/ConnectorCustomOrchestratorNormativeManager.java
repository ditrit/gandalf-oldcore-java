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
    private OrchestratorServiceFeign orchestratorServiceFeign;

    @Autowired
    public ConnectorCustomOrchestratorNormativeManager(ConnectorCustomOrchestratorBashService connectorCustomOrchestratorBashService, OrchestratorServiceFeign orchestratorServiceFeign) {
        this.connectorCustomOrchestratorBashService = connectorCustomOrchestratorBashService;
        this.orchestratorServiceFeign = orchestratorServiceFeign;
        this.mapper = new Gson();
    }

    @Override
    public boolean register(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //return this.connectorCustomOrchestratorBashService.register(jsonObject.get("service").getAsString(), jsonObject.get("version").getAsString());
        return this.orchestratorServiceFeign.register(jsonObject.get("service").getAsString(), jsonObject.get("version").getAsString());
    }

    @Override
    public boolean unregister(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //return this.connectorCustomOrchestratorBashService.unregister(jsonObject.get("service").getAsString(), jsonObject.get("version").getAsString());
        return this.orchestratorServiceFeign.unregister(jsonObject.get("service").getAsString(), jsonObject.get("version").getAsString());
    }

    @Override
    public boolean deploy(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //return this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "deploy");
        return this.orchestratorServiceFeign.deploy(jsonObject.get("service").getAsString());
    }

    @Override
    public boolean undeploy(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //return this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "undeploy");
        return this.orchestratorServiceFeign.undeploy(jsonObject.get("service").getAsString());
    }

    @Override
    public boolean start(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //return this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "start");
        return this.orchestratorServiceFeign.start(jsonObject.get("service").getAsString());
    }

    @Override
    public boolean stop(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //return this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "stop");
        return this.orchestratorServiceFeign.stop(jsonObject.get("service").getAsString());
    }

    @Override
    public boolean scaleUp(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //return this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "scale_up");
        return this.orchestratorServiceFeign.scaleUp(jsonObject.get("service").getAsString());
    }

    @Override
    public boolean scaleDown(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //return this.connectorCustomOrchestratorBashService.execute(jsonObject.get("service").getAsString(), "scale_down");
        return this.orchestratorServiceFeign.scaleDown(jsonObject.get("service").getAsString());
    }

    public boolean downloadProject(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //boolean result = true;
        //result &= this.connectorCustomOrchestratorBashService.downloadProject(jsonObject.get("project_url").getAsString());
        //result &= this.connectorCustomOrchestratorBashService.downloadConfiguration(jsonObject.get("conf_url").getAsString());
        //return result;
        return this.orchestratorServiceFeign.download(jsonObject.get("project_url").getAsString(), jsonObject.get("conf_url").getAsString());
    }
}
