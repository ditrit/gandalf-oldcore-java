package com.ditrit.gandalf.modules.abstractconnectors.abstractorchestrator.manager;

public abstract class ConnectorOrchestratorStandardManager {

    public abstract boolean register(String payload);

    public abstract boolean unregister(String payload);

    public abstract boolean deploy(String payload);

    public abstract boolean undeploy(String payload);

    public abstract boolean start(String payload);

    public abstract boolean stop(String payload);

    public abstract boolean scaleUp(String payload);

    public abstract boolean scaleDown(String payload);
}
