package com.orness.gandalf.core.test.testcommand.core;

import com.orness.gandalf.core.test.testzeromq.Constant;
import com.orness.gandalf.core.test.testzeromq.command.RunnableWorker;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.test.testzeromq.Constant.WORKER_SERVICE_CLASS_ADMIN;
import static com.orness.gandalf.core.test.testzeromq.Constant.WORKER_SERVICE_CLASS_COMMAND_COMMON;

public class GandalfWorkerStartWorker extends RunnableWorker {

    public GandalfWorkerStartWorker(String frontEndCommandConnections) {
        this.initRunnable("TestWorkerCommand", WORKER_SERVICE_CLASS_ADMIN, frontEndCommandConnections);
    }

    @Override
    public Constant.Result executeCommand(ZMsg request) {
        System.out.println(this.workerServiceClassType + " START !!!");
        return Constant.Result.SUCCESS;
    }
}
