package com.orness.gandalf.core.module.nexusmodule.common.worker;

import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;

public class NexusCommonWorkerCommand extends RunnableWorkerZeroMQ {

    public NexusCommonWorkerCommand(String connection) {
        super(connection);
    }

    @Override
    public Object parse(String messageContent) {
        return null;
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

    }
}
