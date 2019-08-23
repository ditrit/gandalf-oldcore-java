package com.orness.gandalf.core.test.testzeromq.gandalf.worker;

import com.orness.gandalf.core.test.testzeromq.Constant;
import com.orness.gandalf.core.test.testzeromq.command.RunnableWorker;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.test.testzeromq.Constant.WORKER_SERVICE_CLASS_COMMAND_COMMON;
import static com.orness.gandalf.core.test.testzeromq.gandalf.GandalfConstant.COMMAND_START;

public class GandalfWorkerStartWorker extends RunnableWorker {

    public GandalfWorkerStartWorker(String frontEndCommandConnections) {
        this.initRunnable("TestWorkerCommand", WORKER_SERVICE_CLASS_COMMAND_COMMON, frontEndCommandConnections);
    }

    @Override
    public Constant.Result executeCommand(ZMsg request) {
        System.out.println(this.workerServiceClassType + " START !!!");
        return Constant.Result.SUCCESS;
    }
}
