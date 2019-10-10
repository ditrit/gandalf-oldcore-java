package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.standard.controller;

import com.ditrit.gandalf.modules.abstractconnectors.abstractworkflowengine.controller.ConnectorWorkflowEngineStandardController;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.standard.manager.ConnectorZeebeStandardManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.properties.ConnectorZeebeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "standardController")
@ConditionalOnBean(ConnectorZeebeProperties.class)
public class ConnectorZeebeStandardController extends ConnectorWorkflowEngineStandardController {

    private ConnectorZeebeStandardManager connectorZeebeStandardManager;

    @Autowired
    public ConnectorZeebeStandardController(ConnectorZeebeStandardManager connectorZeebeStandardManager) {
        this.connectorZeebeStandardManager = connectorZeebeStandardManager;
    }

    @Override
    public String command(String command) {
        return null;
    }

    @Override
    public void event(String event) {

    }
}
