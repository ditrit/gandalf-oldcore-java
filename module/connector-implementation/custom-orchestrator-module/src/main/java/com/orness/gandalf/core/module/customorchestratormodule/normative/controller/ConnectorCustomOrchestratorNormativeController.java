package com.orness.gandalf.core.module.customorchestratormodule.normative.controller;

import com.orness.gandalf.core.module.customorchestratormodule.normative.manager.ConnectorCustomOrchestratorNormativeManager;
import com.orness.gandalf.core.module.customorchestratormodule.properties.ConnectorCustomOrchestratorProperties;
import com.orness.gandalf.core.module.orchestratormodule.controller.ConnectorOrchestratorNormativeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "normativeController")
@ConditionalOnBean(ConnectorCustomOrchestratorProperties.class)
public class ConnectorCustomOrchestratorNormativeController extends ConnectorOrchestratorNormativeController {

    private ConnectorCustomOrchestratorNormativeManager connectorCustomOrchestratorNormativeManager;

    @Autowired
    public ConnectorCustomOrchestratorNormativeController(ConnectorCustomOrchestratorNormativeManager connectorCustomOrchestratorNormativeManager) {
        this.connectorCustomOrchestratorNormativeManager = connectorCustomOrchestratorNormativeManager;
    }

    @Override
    public void command(String command) {

    }

    @Override
    public void event(String event) {

    }
}
