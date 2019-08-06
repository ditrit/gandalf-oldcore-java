package com.orness.gandalf.core.module.workflowenginemodule.constant;

public abstract class WorkflowEngineConstant {

    public static final String COMMAND_DEPLOY = "COMMAND_DEPLOY";
    public static final String COMMAND_INSTANCIATE = "COMMAND_INSTANCIATE";
    public static final String COMMAND_SEND = "COMMAND_SEND";

    public static final String URL_CONTROLLER = "/workflowengine";
    public static final String URL_CONTROLLER_DEPLOY = "/deploy/{workflow}";
    public static final String URL_CONTROLLER_INSTANCIATE = "/instanciate/{id}";
    public static final String URL_CONTROLLER_SEND = "/send/";


}
