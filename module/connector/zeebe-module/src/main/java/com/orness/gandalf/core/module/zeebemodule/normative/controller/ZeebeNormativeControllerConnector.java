package com.orness.gandalf.core.module.zeebemodule.normative.controller;

import com.orness.gandalf.core.module.workflowenginemodule.controller.ConnectorWorkflowEngineNormativeController;
import com.orness.gandalf.core.module.zeebemodule.normative.manager.ZeebeCommonManagerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "commonController")
@Profile(value = "zeebe-module")
public class ZeebeNormativeControllerConnector extends ConnectorWorkflowEngineNormativeController {

    private ZeebeCommonManagerConnector zeebeCommonManager;

    @Autowired
    public ZeebeNormativeControllerConnector(ZeebeCommonManagerConnector zeebeCommonManager) {
        this.zeebeCommonManager = zeebeCommonManager;
    }

    @Override
    public String deployWorkflow(String workflow) {
        return this.zeebeCommonManager.deployWorkflow(workflow);
    }

    @Override
    public void instanciateWorkflow(String id, Object variables) {
        this.zeebeCommonManager.instanciateWorkflow(id, variables);
    }

    @Override
    public void sendMessage(Object message) {
        this.zeebeCommonManager.sendMessage(message);
    }
}
