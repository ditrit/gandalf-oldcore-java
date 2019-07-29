package com.orness.gandalf.core.module.customorchestratormodule.specific.controller;

import com.orness.gandalf.core.module.customorchestratormodule.specific.manager.CustomOrchestratorSpecificManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomOrchestratorSpecificController {

    private CustomOrchestratorSpecificManager customOrchestratorSpecificManager;

    @Autowired
    public CustomOrchestratorSpecificController(CustomOrchestratorSpecificManager customOrchestratorSpecificManager) {
        this.customOrchestratorSpecificManager = customOrchestratorSpecificManager;
    }

    public void untarProject(String projectName, String version) {
        this.customOrchestratorSpecificManager.untarProject(projectName, version);
    }

    public void downloadProject(String projectName, String version) {
        this.customOrchestratorSpecificManager.downloadProject(projectName, version);
    }

    public void downloadConfigurationProject(String projectName, String version) {
        this.customOrchestratorSpecificManager.downloadConfiguration(projectName, version);
    }
}
