package com.orness.gandalf.core.module.customorchestratormodule.custom.controller;

import com.orness.gandalf.core.module.customorchestratormodule.custom.manager.ConnectorCustomOrchestratorCustomManager;
import com.orness.gandalf.core.module.customorchestratormodule.properties.ConnectorCustomOrchestratorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "customController")
@ConditionalOnBean(ConnectorCustomOrchestratorProperties.class)
public class ConnectorCustomOrchestratorCustomController {

    private ConnectorCustomOrchestratorCustomManager connectorCustomOrchestratorCustomManager;

    @Autowired
    public ConnectorCustomOrchestratorCustomController(ConnectorCustomOrchestratorCustomManager connectorCustomOrchestratorCustomManager) {
        this.connectorCustomOrchestratorCustomManager = connectorCustomOrchestratorCustomManager;
    }

    public void untarProject(String projectName, String version) {
        this.connectorCustomOrchestratorCustomManager.untarProject(projectName, version);
    }

    public void downloadProject(String url) {
        this.connectorCustomOrchestratorCustomManager.downloadProject(url);
    }

    public void downloadConfigurationProject(String url) {
        this.connectorCustomOrchestratorCustomManager.downloadConfiguration(url);
    }
}
