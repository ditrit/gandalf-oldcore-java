package com.orness.gandalf.core.module.workflowenginemodule.common.controller;

public abstract class WorkflowEngineCommonController {

    public abstract String deployWorkflow(String workflow);

    public abstract void instanciateWorkflow(String id, Object variables);

    public abstract void sendMessage(Object message);
}
