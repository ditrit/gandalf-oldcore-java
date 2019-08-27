package com.orness.gandalf.core.test.testcommand.core;

import com.orness.gandalf.core.test.testzeromq.Constant;
import com.orness.gandalf.core.test.testzeromq.command.RunnableWorker;
import org.zeromq.ZMsg;

public class GandalfWorker extends RunnableWorker {

    public GandalfWorker(String workerServiceClass, String frontEndWorkerConnections) {
       this.initRunnable(workerServiceClass, frontEndWorkerConnections);
    }

    @Override
    public Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        System.out.println(this.workerServiceClass + " WORKER START !!!");
        return Constant.Result.SUCCESS;
    }

    @Override
    public void executeRoutingSubscriberCommand(ZMsg command) {
        System.out.println(this.workerServiceClass + " SUBSCRIBER START !!!");
    }
}
