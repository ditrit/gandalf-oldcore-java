package com.orness.gandalf.core.module.gitlabmodule.config.common.worker;

import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;

//TODO
public class GitlabCommonWorker extends RunnableWorkerZeroMQ {

    public GitlabCommonWorker(String connection) {
        super(connection);
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

    }
}
