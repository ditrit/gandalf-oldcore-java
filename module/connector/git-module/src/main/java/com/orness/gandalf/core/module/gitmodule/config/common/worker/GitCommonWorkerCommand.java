package com.orness.gandalf.core.module.gitmodule.config.common.worker;

import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;

//TODO
public class GitCommonWorkerCommand extends RunnableWorkerZeroMQ {

    public GitCommonWorkerCommand() {
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
