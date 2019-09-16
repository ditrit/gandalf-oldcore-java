package com.orness.gandalf.core.module.orchestratormodule.manager;

public abstract class ConnectorOrchestratorNormativeManager {

    public abstract boolean register(String payload);

    public abstract boolean unregister(String payload);

    public abstract boolean deploy(String payload);

    public abstract boolean undeploy(String payload);

    public abstract boolean start(String payload);

    public abstract boolean stop(String payload);

    public abstract boolean scaleUp(String payload);

    public abstract boolean scaleDown(String payload);
}
