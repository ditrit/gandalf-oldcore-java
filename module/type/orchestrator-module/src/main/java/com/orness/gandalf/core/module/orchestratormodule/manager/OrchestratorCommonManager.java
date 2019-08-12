package com.orness.gandalf.core.module.orchestratormodule.manager;

public abstract class OrchestratorCommonManager {

    public abstract void register(String service, String version);

    public abstract void unregister(String service, String version);

    public abstract void deploy(String service);

    public abstract void undeploy(String service);

    public abstract void start(String service);

    public abstract void stop(String service);

    public abstract void scaleUp(String service);

    public abstract void scaleDown(String service);
}
