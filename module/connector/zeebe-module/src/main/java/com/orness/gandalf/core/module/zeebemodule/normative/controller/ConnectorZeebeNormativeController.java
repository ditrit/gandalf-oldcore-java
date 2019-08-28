package com.orness.gandalf.core.module.zeebemodule.normative.controller;

import com.orness.gandalf.core.module.workflowenginemodule.controller.ConnectorWorkflowEngineNormativeController;
import com.orness.gandalf.core.module.zeebemodule.normative.manager.ConnectorZeebeNormativeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "commonController")
@Profile(value = "zeebe-module")
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
