package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.normative.controller;

import com.ditrit.gandalf.modules.abstractconnectors.abstractorchestrator.controller.ConnectorOrchestratorNormativeController;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.normative.manager.ConnectorCustomOrchestratorNormativeManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.properties.ConnectorCustomOrchestratorProperties;
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
