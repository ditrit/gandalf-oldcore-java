package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.custom.manager;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.core.ConnectorCustomOrchestratorBashService;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.properties.ConnectorCustomOrchestratorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component(value = "customManager")
@ConditionalOnBean(ConnectorCustomOrchestratorProperties.class)
public class ConnectorCustomOrchestratorCustomManager {

    private ConnectorCustomOrchestratorBashService connectorCustomOrchestratorBashService;

    @Autowired
    public ConnectorCustomOrchestratorCustomManager(ConnectorCustomOrchestratorBashService connectorCustomOrchestratorBashService) {
        this.connectorCustomOrchestratorBashService = connectorCustomOrchestratorBashService;
    }

    public void untarProject(String projectName, String version) {
        this.connectorCustomOrchestratorBashService.untarProject(projectName, version);
    }

    public void downloadProject(String url) {
        this.connectorCustomOrchestratorBashService.downloadProject(url);
    }

    public void downloadConfiguration(String url) {
        this.connectorCustomOrchestratorBashService.downloadConfiguration(url);
    }
}
