package com.orness.gandalf.core.module.orchestratormodule.manager;

public abstract class ConnectorOrchestratorNormativeManager {

    public abstract void register(String payload);

    public abstract void unregister(String payload);

    public abstract void deploy(String payload);

    public abstract void undeploy(String payload);

    public abstract void start(String payload);

    public abstract void stop(String payload);

    public abstract void scaleUp(String payload);

    public abstract void scaleDown(String payload);
}
