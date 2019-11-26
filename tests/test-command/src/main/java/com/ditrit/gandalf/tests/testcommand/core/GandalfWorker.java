/*
package com.orness.gandalf.com.ditrit.gandalf.gandalfjava.functions.functionszeebe.core.test.testcommand.com.ditrit.gandalf.gandalfjava.functions.functionszeebe.core;

import com.orness.gandalf.com.ditrit.gandalf.gandalfjava.functions.functionszeebe.core.test.testzeromq.Constant;
import com.orness.gandalf.com.ditrit.gandalf.gandalfjava.functions.functionszeebe.core.test.testzeromq.command.RunnableWorker;
import org.zeromq.ZMsg;

import java.util.List;

public class GandalfWorker extends RunnableWorker {

    public GandalfWorker(String workerServiceClass, String frontEndWorkerConnections, String frontEndSubscriberWorkerConnections, List<String> topics) {
        this.initRunnable(workerServiceClass, frontEndWorkerConnections, frontEndSubscriberWorkerConnections, topics);
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
*/
