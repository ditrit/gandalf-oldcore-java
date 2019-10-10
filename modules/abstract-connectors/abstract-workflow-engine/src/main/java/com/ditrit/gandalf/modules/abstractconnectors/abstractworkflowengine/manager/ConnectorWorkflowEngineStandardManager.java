package com.ditrit.gandalf.modules.abstractconnectors.abstractworkflowengine.manager;

public abstract class ConnectorWorkflowEngineStandardManager {

    public abstract String deployWorkflow(String payload);

    public abstract void instanciateWorkflow(String payload);

    public abstract void sendMessage(String payload);
}
