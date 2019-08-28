package com.orness.gandalf.core.module.customorchestratormodule.custom.manager;

import com.orness.gandalf.core.module.customorchestratormodule.core.ConnectorCustomOrchestratorBashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "specificManager")
@Profile(value = "custom-orchestrator-module")
public class ConnectorCustomOrchestratorCustomManager {

    private ConnectorCustomOrchestratorBashService connectorCustomOrchestratorBashService;

    @Autowired
    public ConnectorCustomOrchestratorCustomManager(ConnectorCustomOrchestratorBashService connectorCustomOrchestratorBashService) {
        this.connectorCustomOrchestratorBashService = connectorCustomOrchestratorBashService;
    }

    public void untarProject(String projectName, String version) {
        this.connectorCustomOrchestratorBashService.untarProject(projectName, version);
    }

    public void downloadProject(String projectName, String version) {
        this.connectorCustomOrchestratorBashService.downloadProject(projectName, version);
    }

    public void downloadConfiguration(String projectName, String version) {
        this.connectorCustomOrchestratorBashService.downloadConfiguration(projectName, version);
    }
}
