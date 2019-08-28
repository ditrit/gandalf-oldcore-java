package com.orness.gandalf.core.module.customorchestratormodule.normative.controller;

import com.orness.gandalf.core.module.customorchestratormodule.normative.manager.ConnectorCustomOrchestratorNormativeManager;
import com.orness.gandalf.core.module.orchestratormodule.controller.ConnectorOrchestratorNormativeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "commonController")
@Profile(value = "custom-orchestrator-module")
public class ConnectorCustomOrchestratorNormativeController extends ConnectorOrchestratorNormativeController {

    private ConnectorCustomOrchestratorNormativeManager customOrchestratorCommonManager;

    @Autowired
    public ConnectorCustomOrchestratorNormativeController(ConnectorCustomOrchestratorNormativeManager customOrchestratorCommonManager) {
        this.customOrchestratorCommonManager = customOrchestratorCommonManager;
    }

    @Override
    public void command(String command) {

    }

    @Override
    public void event(String event) {

    }
}
