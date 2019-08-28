package com.orness.gandalf.core.module.gitmodule.config.custom.worker;


import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.zeromq.ZMsg;

//TODO
public class ConnectorGitCustomWorker extends RunnableWorkerZeroMQ {


    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        return null;
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {

    }
}
