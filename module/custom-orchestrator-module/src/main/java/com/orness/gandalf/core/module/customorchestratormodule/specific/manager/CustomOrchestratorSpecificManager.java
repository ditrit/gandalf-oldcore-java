package com.orness.gandalf.core.module.customorchestratormodule.specific.manager;

import com.orness.gandalf.core.module.customorchestratormodule.core.CustomOrchestratorBashService;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomOrchestratorSpecificManager {

    private CustomOrchestratorBashService customOrchestratorBashService;

    @Autowired
    public CustomOrchestratorSpecificManager(CustomOrchestratorBashService customOrchestratorBashService) {
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
