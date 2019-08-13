package com.orness.gandalf.core.module.busmodule.constant;

public abstract class BusConstant {

    public static final String COMMAND_CREATE_TOPIC = "COMMAND_CREATE_TOPIC";
    public static final String COMMAND_DELETE_TOPIC = "COMMAND_DELETE_TOPIC";
    public static final String COMMAND_SEND_MESSAGE = "COMMAND_SEND_MESSAGE";
    public static final String COMMAND_RECEIVE_MESSAGE = "COMMAND_RECEIVE_MESSAGE";
    public static final String COMMAND_SYNCHRONIZE_GANDALF = "COMMAND_SYNCHRONIZE_GANDALF";
    public static final String COMMAND_SYNCHRONIZE_BUS = "COMMAND_SYNCHRONIZE_BUS";

    public static final String URL_CONTROLLER = "/bus";
    public static final String URL_CONTROLLER_CREATE_TOPIC = "/topic/create/{topic}";
    public static final String URL_CONTROLLER_DELETE_TOPIC = "/topic/delete/{topic}";
    public static final String URL_CONTROLLER_SEND_MESSAGE = "/message/send/{topic}";
    public static final String URL_CONTROLLER_RECEIVE_MESSAGE = "/message/receive/{topic}";
    public static final String URL_CONTROLLER_SYNCHRONIZE_GANDALF = "/synchronize/gandalf/{topic}";
    public static final String URL_CONTROLLER_SYNCHRONIZE_BUS = "/synchronize/bus/{topic}";
}
