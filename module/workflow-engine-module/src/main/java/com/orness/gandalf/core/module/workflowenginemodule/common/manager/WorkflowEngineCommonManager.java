package com.orness.gandalf.core.module.workflowenginemodule.common.manager;

import java.util.HashMap;

public abstract class WorkflowEngineCommonManager {

    public abstract String deployWorkflow(String workflow);

    public abstract void instanciateWorkflow(String id, Object variables);

    public abstract void sendMessage(Object message);
}
