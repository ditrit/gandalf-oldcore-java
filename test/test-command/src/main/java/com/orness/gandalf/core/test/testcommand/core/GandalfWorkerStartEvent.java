package com.orness.gandalf.core.test.testcommand.core;

import com.orness.gandalf.core.test.testzeromq.Constant;
import com.orness.gandalf.core.test.testzeromq.command.RunnableWorker;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.test.testzeromq.Constant.WORKER_SERVICE_CLASS_EVENT_COMMON;

public class GandalfWorkerStartEvent extends RunnableWorker {

    public GandalfWorkerStartEvent(String frontEndWorkerConnections) {
       this.initRunnable("TestWorkerEvent", WORKER_SERVICE_CLASS_EVENT_COMMON, frontEndWorkerConnections);
    }

    @Override
    public Constant.Result executeCommand(ZMsg request) {
        System.out.println(this.workerServiceClassType + " START !!!");
        return Constant.Result.SUCCESS;
    }
}
