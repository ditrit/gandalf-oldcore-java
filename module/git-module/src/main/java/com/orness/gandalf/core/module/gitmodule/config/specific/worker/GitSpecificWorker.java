package com.orness.gandalf.core.module.gitmodule.config.specific.worker;

import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;

public class GitSpecificWorker extends RunnableWorkerZeroMQ {

    public GitSpecificWorker(String connection) {
        super(connection);
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

    }
}
