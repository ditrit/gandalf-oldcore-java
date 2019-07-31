package com.orness.gandalf.core.module.orchestratormodule.constant;

public abstract class OrchestratorConstant {

    public static final String COMMAND_REGISTER = "COMMAND_REGISTER";
    public static final String COMMAND_UNREGISTER = "COMMAND_UNREGISTER";
    public static final String COMMAND_DEPLOY = "COMMAND_DEPLOY";
    public static final String COMMAND_UNDEPLOY = "COMMAND_UNDEPLOY";
    public static final String COMMAND_START = "COMMAND_START";
    public static final String COMMAND_STOP = "COMMAND_STOP";
    public static final String COMMAND_SCALE_UP = "COMMAND_SCALE_UP";
    public static final String COMMAND_SCALE_DOWN = "COMMAND_SCALE_DOWN";

    public static final String URL_CONTROLLER = "/orchestrator";
    public static final String URL_CONTROLLER_REGISTER = "/register/{service}/{version}";
    public static final String URL_CONTROLLER_UNREGISTER = "/unregister/{service}/{version}";
    public static final String URL_CONTROLLER_DEPLOY = "/deploy/{service}";
    public static final String URL_CONTROLLER_UNDEPLOY = "/undeploy/{service}";
    public static final String URL_CONTROLLER_START = "/start/{service}";
    public static final String URL_CONTROLLER_STOP = "/stop/{service}";
    public static final String URL_CONTROLLER_SCALE_UP = "/scaleup/{service}";
    public static final String URL_CONTROLLER_SCALE_DOWN = "/scaledown/{service}";

}
