package com.orness.gandalf.core.module.gandalfmodule.constant;

public abstract class GandalfConstant {

    public static final String WORKER_DIRECTORY = "gandalf/workers/";

    public static final String COMMAND_TYPE_GANDALF = "COMMAND_TYPE_GANDALF";
    public static final String COMMAND_START = "COMMAND_START";
    public static final String COMMAND_STOP = "COMMAND_STOP";
    public static final String COMMAND_SUBSCRIBE = "COMMAND_SUBSCRIBE";
    public static final String COMMAND_UNSUBSCRIBE = "COMMAND_UNSUBSCRIBE";
    public static final String COMMAND_PUBLISH = "COMMAND_PUBLISH";

    public static final String URL_CONTROLLER = "/gandalf";
    public static final String URL_CONTROLLER_START = "/start";
    public static final String URL_CONTROLLER_STOP = "/stop";
    public static final String URL_CONTROLLER_SUBSCRIBE = "/subscribe";
    public static final String URL_CONTROLLER_UNSUBSCRIBE = "/unsubscribe";
    public static final String URL_CONTROLLER_PUBLISH = "/publish/{event}";
}
