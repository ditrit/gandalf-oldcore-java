package com.ditrit.gandalf.tests.testzeromq.gandalf.worker;

import com.ditrit.gandalf.tests.testzeromq.command.RunnableWorker;
import com.ditrit.gandalf.tests.testzeromq.Constant;
import org.zeromq.ZMsg;

import java.util.List;

import static com.ditrit.gandalf.tests.testzeromq.Constant.WORKER_SERVICE_CLASS_ADMIN;

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
