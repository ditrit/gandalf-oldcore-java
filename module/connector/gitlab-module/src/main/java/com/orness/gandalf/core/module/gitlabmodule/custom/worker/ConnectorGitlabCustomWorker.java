package com.orness.gandalf.core.module.gitlabmodule.custom.worker;

import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "customWorker")
@Profile(value = "gitlab")
public class ConnectorGitlabCustomWorker extends RunnableWorkerZeroMQ {


    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        return null;
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {

    }
}