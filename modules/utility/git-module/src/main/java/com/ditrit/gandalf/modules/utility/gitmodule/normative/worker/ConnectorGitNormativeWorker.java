package com.ditrit.gandalf.modules.utility.gitmodule.normative.worker;


import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.RunnableWorkerZeroMQ;
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
