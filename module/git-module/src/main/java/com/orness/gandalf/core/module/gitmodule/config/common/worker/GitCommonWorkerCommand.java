package com.orness.gandalf.core.module.gitmodule.config.common.worker;

import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;

public class GitCommonWorkerCommand extends RunnableWorkerZeroMQ {

    public GitCommonWorkerCommand(String connection) {
        super(connection);
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

    }
}
