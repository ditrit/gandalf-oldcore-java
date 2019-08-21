package com.orness.gandalf.core.test.testzeromq;

public class Constant {

    public enum Result {
        SUCCESS,
        FAIL
    }

    //COMMAND CLIENT

    //COMMAND WORKER
    public static final String WORKER_COMMAND_READY = "WORKER_COMMAND_READY";
    public static final String WORKER_COMMAND_RESULT = "WORKER_COMMAND_RESULT";

    //COMMAND COMMAND
    public static final String COMMAND_COMMAND_READY = "COMMAND_COMMAND_READY";
    public static final String COMMAND_COMMAND_RESULT = "COMMAND_COMMAND_RESULT";

    //COMMAND SERVICE
    //public static final String INTERNAL_SERVICE_PREFIX = "INTERNAL_SERVICE_PREFIX";
    public static final String WORKER_COMMAND_SERVICE_GANDALF = "WORKER_COMMAND_SERVICE_GANDALF";
    public static final String WORKER_COMMAND_SERVICE_COMMON = "WORKER_COMMAND_SERVICE_COMMON";
    public static final String WORKER_COMMAND_SERVICE_SPECIFIC = "WORKER_COMMAND_SERVICE_SPECIFIC";

/*    //TODO REVOIR
    //COMMAND TYPE
    public static final String COMMAND_CLIENT_REQUEST = "COMMAND_CLIENT_REQUEST";
    public static final String COMMAND_WORKER_RESPONSE = "COMMAND_WORKER_RESPONSE";
    public static final String COMMAND_WORKER_BROKER = "COMMAND_WORKER_BROKER";
    public static final String COMMAND_TYPE_BROKER = "COMMAND_TYPE_BROKER";

    //COMMAND
    public static final String COMMAND_WORKER_READY = "COMMAND_WORKER_READY";
    public static final String COMMAND_WORKER_HEARTBEAT = "COMMAND_WORKER_HEARTBEAT";
    public static final String COMMAND_WORKER_DISCONNECT = "COMMAND_WORKER_DISCONNECT";*/


}
