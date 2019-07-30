package com.orness.gandalf.core.module.customorchestratormodule.common.manager;

import com.orness.gandalf.core.module.customorchestratormodule.core.CustomOrchestratorBashService;
import com.orness.gandalf.core.module.orchestratormodule.manager.OrchestratorCommonManager;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomOrchestratorCommonManager extends OrchestratorCommonManager {

    private CustomOrchestratorBashService customOrchestratorBashService;

    @Autowired
    public CustomOrchestratorCommonManager(CustomOrchestratorBashService customOrchestratorBashService) {
        this.customOrchestratorBashService = customOrchestratorBashService;
    }

    @Override
    public void register(String service, String version) {
        this.customOrchestratorBashService.register(service, version);
    }

    @Override
    public void unregister(String service, String version) {
        this.customOrchestratorBashService.unregister(service, version);
    }

    @Override
    public void deploy(String service) {
        this.customOrchestratorBashService.execute(service, "deploy");
    }

    @Override
    public void undeploy(String service) {
        this.customOrchestratorBashService.execute(service, "undeploy");
    }

    @Override
    public void start(String service) {
        this.customOrchestratorBashService.execute(service, "start");
    }

    @Override
    public void stop(String service) {
        this.customOrchestratorBashService.execute(service, "stop");
    }

    @Override
    public void scaleUp(String service) {
        this.customOrchestratorBashService.execute(service, "scale_up");
    }

    @Override
    public void scaleDown(String service) {
        this.customOrchestratorBashService.execute(service, "scale_down");
    }
}
