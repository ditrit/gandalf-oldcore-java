package com.orness.gandalf.core.test.testzeromq.gandalf.worker;

import com.orness.gandalf.core.test.testzeromq.Constant;
import com.orness.gandalf.core.test.testzeromq.command.RunnableWorker;
import org.zeromq.ZMsg;

import java.util.List;

import static com.orness.gandalf.core.test.testzeromq.Constant.WORKER_SERVICE_CLASS_ADMIN;

public class GandalfWorkerStartEvent extends RunnableWorker {

    public GandalfWorkerStartEvent(String frontEndWorkerConnections, String frontEndSubscriberWorkerConnections, List<String> topics) {
       this.initRunnable(WORKER_SERVICE_CLASS_ADMIN, frontEndWorkerConnections, frontEndSubscriberWorkerConnections, topics);
    }

    @Override
    public Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        System.out.println(this.workerServiceClass + " START !!!");
        return Constant.Result.SUCCESS;
    }

    @Override
    public void executeRoutingSubscriberCommand(ZMsg command) {
        System.out.println(this.workerServiceClass + " START !!!");
    }
}
