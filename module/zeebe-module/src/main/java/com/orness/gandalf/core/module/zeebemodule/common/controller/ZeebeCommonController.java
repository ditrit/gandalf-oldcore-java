package com.orness.gandalf.core.module.zeebemodule.common.controller;

import com.orness.gandalf.core.module.workflowenginemodule.common.controller.WorkflowEngineCommonController;
import com.orness.gandalf.core.module.zeebemodule.common.manager.ZeebeCommonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZeebeCommonController extends WorkflowEngineCommonController {

    private ZeebeCommonManager zeebeCommonManager;

    @Autowired
    public ZeebeCommonController(ZeebeCommonManager zeebeCommonManager) {
        this.zeebeCommonManager = zeebeCommonManager;
    }

    @Override
    public String deployWorkflow(String workflow) {
        return this.zeebeCommonManager.deployWorkflow("");
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
