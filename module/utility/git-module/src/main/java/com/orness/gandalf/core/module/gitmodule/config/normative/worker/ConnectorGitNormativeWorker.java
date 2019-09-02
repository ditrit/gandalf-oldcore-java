package com.orness.gandalf.core.module.gitmodule.config.normative.worker;


import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.zeromq.ZMsg;

//TODO
public class ConnectorGitNormativeWorker extends RunnableWorkerZeroMQ {


    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        return null;
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {

    }
}
