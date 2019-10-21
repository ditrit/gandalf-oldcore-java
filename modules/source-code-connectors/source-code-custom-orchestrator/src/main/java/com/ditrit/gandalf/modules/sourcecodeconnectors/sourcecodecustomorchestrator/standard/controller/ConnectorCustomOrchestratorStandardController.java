package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.standard.controller;

import com.ditrit.gandalf.modules.abstractconnectors.abstractorchestrator.controller.ConnectorOrchestratorStandardController;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.standard.manager.ConnectorCustomOrchestratorStandardManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.properties.ConnectorCustomOrchestratorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "standardController")
@ConditionalOnBean(ConnectorCustomOrchestratorProperties.class)
public class ConnectorCustomOrchestratorStandardController extends ConnectorOrchestratorStandardController {

    private ConnectorCustomOrchestratorStandardManager connectorCustomOrchestratorStandardManager;

    @Autowired
    public ConnectorCustomOrchestratorStandardController(ConnectorCustomOrchestratorStandardManager connectorCustomOrchestratorStandardManager) {
        this.connectorCustomOrchestratorStandardManager = connectorCustomOrchestratorStandardManager;
    }

    @Override
    public void command(String command) {

    }

    @Override
    public void event(String event) {

    }
}
