package com.ditrit.gandalf.core.zeromqcore.constant;

import java.util.Arrays;
import java.util.List;

public class Constant {

    public enum Result {
        SUCCESS,
        FAIL
    }

    //COMMAND COMMAND
    public static final String COMMAND_CLIENT_SEND = "COMMAND_CLIENT_SEND";
    public static final String EVENT_CLIENT_SEND = "EVENT_CLIENT_SEND";
    public static final String COMMAND_READY = "COMMAND_READY";
    public static final String COMMAND_RESULT = "COMMAND_RESULT";

    public static final List<String> GANDALF_SERVICECLASS = Arrays.asList("WORKER_SERVICE_CLASS_ADMIN", "WORKER_SERVICE_CLASS_STANDARD", "WORKER_SERVICE_CLASS_CUSTOM");

}
