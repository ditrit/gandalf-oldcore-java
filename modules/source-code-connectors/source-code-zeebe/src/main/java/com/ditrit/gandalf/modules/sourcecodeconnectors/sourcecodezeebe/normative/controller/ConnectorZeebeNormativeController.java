package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.normative.controller;

import com.ditrit.gandalf.modules.abstractconnectors.abstractworkflowengine.controller.ConnectorWorkflowEngineNormativeController;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.normative.manager.ConnectorZeebeNormativeManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.properties.ConnectorZeebeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "normativeController")
@ConditionalOnBean(ConnectorZeebeProperties.class)
public class ConnectorZeebeNormativeController extends ConnectorWorkflowEngineNormativeController {

    private ConnectorZeebeNormativeManager connectorZeebeNormativeManager;

    @Autowired
    public ConnectorZeebeNormativeController(ConnectorZeebeNormativeManager connectorZeebeNormativeManager) {
        this.connectorZeebeNormativeManager = connectorZeebeNormativeManager;
    }

    @Override
    public String command(String command) {
        return null;
    }

    @Override
    public void event(String event) {

    }
}
