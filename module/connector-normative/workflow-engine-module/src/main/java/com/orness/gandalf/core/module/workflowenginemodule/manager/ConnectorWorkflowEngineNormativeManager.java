package com.orness.gandalf.core.module.workflowenginemodule.manager;

public abstract class ConnectorWorkflowEngineNormativeManager {

    public abstract String deployWorkflow(String payload);

    public abstract void instanciateWorkflow(String payload);

    public abstract void sendMessage(String payload);
}
