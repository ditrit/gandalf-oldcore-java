package com.orness.gandalf.core.module.versioncontrolmodule.constant;

public abstract class VersionControlConstant {

    public static final String COMMAND_CLONE = "COMMAND_CLONE";
    public static final String COMMAND_PULL = "COMMAND_PULL";
    public static final String COMMAND_ADD = "COMMAND_ADD";
    public static final String COMMAND_COMMIT = "COMMAND_COMMIT";
    public static final String COMMAND_PUSH = "COMMAND_PUSH";
    public static final String COMMAND_MERGE = "COMMAND_MERGE";

    public static final String URL_CONTROLLER = "/versioncontrol";
    public static final String URL_CONTROLLER_CLONE = "/clone/{url}";
    public static final String URL_CONTROLLER_PULL = "/pull/{url}";
    public static final String URL_CONTROLLER_ADD = "/add/{path}";
    public static final String URL_CONTROLLER_COMMIT = "/commit/{message}";
    public static final String URL_CONTROLLER_PUSH = "/push/{origin}/{branch}";
    public static final String URL_CONTROLLER_MERGE = "/merge/{source}/{destination}";

}
