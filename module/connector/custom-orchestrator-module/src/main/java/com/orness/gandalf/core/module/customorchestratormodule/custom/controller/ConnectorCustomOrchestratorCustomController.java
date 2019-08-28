package com.orness.gandalf.core.module.customorchestratormodule.custom.controller;

import com.orness.gandalf.core.module.customorchestratormodule.custom.manager.ConnectorCustomOrchestratorCustomManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "specificController")
@Profile(value = "custom-orchestrator-module")
public class ConnectorCustomOrchestratorCustomController {

    private ConnectorCustomOrchestratorCustomManager connectorCustomOrchestratorCustomManager;

    @Autowired
    public ConnectorCustomOrchestratorCustomController(ConnectorCustomOrchestratorCustomManager connectorCustomOrchestratorCustomManager) {
        this.connectorCustomOrchestratorCustomManager = connectorCustomOrchestratorCustomManager;
    }

    public void untarProject(String projectName, String version) {
        this.connectorCustomOrchestratorCustomManager.untarProject(projectName, version);
    }

    public void downloadProject(String projectName, String version) {
        this.connectorCustomOrchestratorCustomManager.downloadProject(projectName, version);
    }

    public void downloadConfigurationProject(String projectName, String version) {
        this.connectorCustomOrchestratorCustomManager.downloadConfiguration(projectName, version);
    }
}
