package com.orness.gandalf.core.module.databasemodule.constant;

public abstract class DatabaseConstant {

    public static final String COMMAND_SELECT = "COMMAND_SELECT";
    public static final String COMMAND_INSERT = "COMMAND_INSERT";
    public static final String COMMAND_UPDATE = "COMMAND_UPDATE";
    public static final String COMMAND_DELETE = "COMMAND_DELETE";

    public static final String URL_CONTROLLER = "/database";
    public static final String URL_CONTROLLER_SELECT = "/select/{object}";
    public static final String URL_CONTROLLER_INSERT = "/insert/{topic}";
    public static final String URL_CONTROLLER_UPDATE = "/update/{topic}";
    public static final String URL_CONTROLLER_DELETE = "/delete/{topic}";


}