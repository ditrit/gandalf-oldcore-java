package com.orness.gandalf.core.module.nexusmodule.specific.worker;

import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;

public class NexusSpecificWorkerCommand extends RunnableWorkerZeroMQ {

    public NexusSpecificWorkerCommand(String connection) {
        super(connection);
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

    }
}
