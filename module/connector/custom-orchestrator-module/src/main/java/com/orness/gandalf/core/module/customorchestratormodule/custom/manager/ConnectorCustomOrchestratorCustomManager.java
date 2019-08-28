package com.orness.gandalf.core.module.customorchestratormodule.custom.manager;

import com.orness.gandalf.core.module.customorchestratormodule.core.CustomOrchestratorBashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "specificManager")
@Profile(value = "custom-orchestrator-module")
public class ConnectorCustomOrchestratorCustomManager {

    private CustomOrchestratorBashService customOrchestratorBashService;

    @Autowired
    public ConnectorCustomOrchestratorCustomManager(CustomOrchestratorBashService customOrchestratorBashService) {
        this.customOrchestratorBashService = customOrchestratorBashService;
    }

    public void untarProject(String projectName, String version) {
        this.customOrchestratorBashService.untarProject(projectName, version);
    }

    public void downloadProject(String projectName, String version) {
        this.customOrchestratorBashService.downloadProject(projectName, version);
    }

    public void downloadConfiguration(String projectName, String version) {
        this.customOrchestratorBashService.downloadConfiguration(projectName, version);
    }
}
