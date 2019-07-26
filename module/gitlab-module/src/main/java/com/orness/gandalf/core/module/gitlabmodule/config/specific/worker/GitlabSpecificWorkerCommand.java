package com.orness.gandalf.core.module.gitlabmodule.config.specific.worker;

import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
//TODO
public class GitlabSpecificWorkerCommand extends RunnableWorkerZeroMQ {

    public GitlabSpecificWorkerCommand(String connection) {
        super(connection);
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

    }
}
