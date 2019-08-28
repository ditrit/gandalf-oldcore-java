package com.orness.gandalf.core.module.customorchestratormodule.normative.manager;

import com.orness.gandalf.core.module.customorchestratormodule.core.ConnectorCustomOrchestratorBashService;
import com.orness.gandalf.core.module.orchestratormodule.manager.ConnectorOrchestratorNormativeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "commonManager")
@Profile(value = "custom-orchestrator-module")
public class ConnectorCustomOrchestratorNormativeManager extends ConnectorOrchestratorNormativeManager {

    private ConnectorCustomOrchestratorBashService connectorCustomOrchestratorBashService;

    @Autowired
    public ConnectorCustomOrchestratorNormativeManager(ConnectorCustomOrchestratorBashService connectorCustomOrchestratorBashService) {
        this.connectorCustomOrchestratorBashService = connectorCustomOrchestratorBashService;
    }

    @Override
    public void register(String service, String version) {
        this.connectorCustomOrchestratorBashService.register(service, version);
    }

    @Override
    public void unregister(String service, String version) {
        this.connectorCustomOrchestratorBashService.unregister(service, version);
    }

    @Override
    public void deploy(String service) {
        this.connectorCustomOrchestratorBashService.execute(service, "deploy");
    }

    @Override
    public void undeploy(String service) {
        this.connectorCustomOrchestratorBashService.execute(service, "undeploy");
    }

    @Override
    public void start(String service) {
        this.connectorCustomOrchestratorBashService.execute(service, "start");
    }

    @Override
    public void stop(String service) {
        this.connectorCustomOrchestratorBashService.execute(service, "stop");
    }

    @Override
    public void scaleUp(String service) {
        this.connectorCustomOrchestratorBashService.execute(service, "scale_up");
    }

    @Override
    public void scaleDown(String service) {
        this.connectorCustomOrchestratorBashService.execute(service, "scale_down");
    }
}
