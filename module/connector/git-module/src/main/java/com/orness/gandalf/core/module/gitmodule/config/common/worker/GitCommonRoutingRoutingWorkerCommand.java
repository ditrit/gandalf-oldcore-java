package com.orness.gandalf.core.module.gitmodule.config.common.worker;

import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.routing.RunnableRoutingRoutingWorkerZeroMQ;

//TODO
public class GitCommonRoutingRoutingWorkerCommand extends RunnableRoutingRoutingWorkerZeroMQ {

    public GitCommonRoutingRoutingWorkerCommand() {
        super();
    }

    @Override
    public Object parse(String messageContent) {
        return null;
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

    }
}
