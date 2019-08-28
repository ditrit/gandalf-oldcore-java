package com.orness.gandalf.core.module.customorchestratormodule.normative.controller;

import com.orness.gandalf.core.module.customorchestratormodule.normative.manager.CustomOrchestratorCommonManager;
import com.orness.gandalf.core.module.orchestratormodule.controller.OrchestratorCommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "commonController")
@Profile(value = "custom-orchestrator-module")
public class CustomOrchestratorCommonController extends OrchestratorCommonController {

    private CustomOrchestratorCommonManager  customOrchestratorCommonManager;

    @Autowired
    public CustomOrchestratorCommonController(CustomOrchestratorCommonManager customOrchestratorCommonManager) {
        this.customOrchestratorCommonManager = customOrchestratorCommonManager;
    }

    @Override
    public void register(String service, String version) {
        this.customOrchestratorCommonManager.register(service, version);
    }

    @Override
    public void unregister(String service, String version) {
        this.customOrchestratorCommonManager.unregister(service, version);
    }

    @Override
    public void deploy(String service) {
        this.customOrchestratorCommonManager.deploy(service);
    }

    @Override
    public void undeploy(String service) {
        this.customOrchestratorCommonManager.undeploy(service);
    }

    @Override
    public void start(String service) {
        this.customOrchestratorCommonManager.start(service);
    }

    @Override
    public void stop(String service) {
        this.customOrchestratorCommonManager.stop(service);
    }

    @Override
    public void scaleUp(String service) {
        this.customOrchestratorCommonManager.scaleUp(service);
    }

    @Override
    public void scaleDown(String service) {
        this.customOrchestratorCommonManager.scaleDown(service);
    }
}
