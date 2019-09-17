package com.ditrit.gandalf.tests.testzeromq;

public class Constant {

    public enum Result {
        SUCCESS,
        FAIL
    }

    //COMMAND ROUTING WORKER TYPE
    public static final String ROUTING_WORKER = "ROUTING_WORKER";
    public static final String ROUTING_SUBSCRIBER = "ROUTING_SUBSCRIBER";

    //COMMAND WORKER
    public static final String WORKER_COMMAND_READY = "WORKER_COMMAND_READY";
    public static final String WORKER_COMMAND_RESULT = "WORKER_COMMAND_RESULT";

    //COMMAND COMMAND
    public static final String COMMAND_COMMAND_READY = "COMMAND_COMMAND_READY";
    public static final String COMMAND_COMMAND_RESULT = "COMMAND_COMMAND_RESULT";

    //COMMAND SERVICE CLASS
    public static final String WORKER_SERVICE_CLASS_ADMIN = "WORKER_SERVICE_CLASS_ADMIN";
    public static final String WORKER_SERVICE_CLASS_COMMAND = "WORKER_SERVICE_CLASS_COMMAND";
    public static final String WORKER_SERVICE_CLASS_EVENT = "WORKER_SERVICE_CLASS_EVENT";

    //COMMAND SERVICE CLASS TYPE
    public static final String WORKER_SERVICE_CLASS_COMMAND_COMMON = "WORKER_SERVICE_CLASS_COMMAND_COMMON";
    public static final String WORKER_SERVICE_CLASS_COMMAND_UNCOMMON = "WORKER_SERVICE_CLASS_COMMAND_UNCOMMON";
    public static final String WORKER_SERVICE_CLASS_EVENT_COMMON = "WORKER_SERVICE_CLASS_EVENT_COMMON";
    public static final String WORKER_SERVICE_CLASS_EVENT_UNCOMMON = "WORKER_SERVICE_CLASS_EVENT_UNCOMMON";


    //COMMAND EVENT
    public static final String WORKER_COMMAND_EVENT = "WORKER_COMMAND_EVENT";


}
