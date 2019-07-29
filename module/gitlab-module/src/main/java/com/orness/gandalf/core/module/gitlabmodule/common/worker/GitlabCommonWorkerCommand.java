package com.orness.gandalf.core.module.gitlabmodule.common.worker;

import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;

//TODO
public class GitlabCommonWorkerCommand extends RunnableWorkerZeroMQ {

    public GitlabCommonWorkerCommand(String connection) {
        super(connection);
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

    }
}
