package com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant;

import java.util.Arrays;
import java.util.List;

public class Constant {

    //ENUM
    public enum State {
        ONGOING,
        DONE,
        ERROR
    }

    public enum Result {
        SUCCES,
        FAIL
    }

    //PATH
    public static final String ROOT_PATH = "/usr/lib/gandalf/connectors/";
    public static final String WORKERS_PATH = "/workers/";
    public static final String FUNCTIONS_PATH = "/functions/";

    //COMMAND
    public static final String COMMAND_READY = "COMMAND_READY";
    public static final String WORKER_SERVICE_CLASS_CAPTURE = "WORKER_SERVICE_CLASS_CAPTURE";
    public static final List<String> GANDALF_SERVICECLASS = Arrays.asList("WORKER_SERVICE_CLASS_ADMIN", "WORKER_SERVICE_CLASS_STANDARD", "WORKER_SERVICE_CLASS_CUSTOM");

}
