package com.orness.gandalf.core.module.zeromqmodule.command.worker;

import com.orness.gandalf.core.module.zeromqmodule.command.domain.CommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.ICommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;

public abstract  class RunnableWorkerZeroMQ extends WorkerZeroMQ implements ICommandZeroMQ, Runnable {

    public RunnableWorkerZeroMQ(String connection) {
        super(connection);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            MessageCommandZeroMQ messageCommandZeroMQ = CommandZeroMQ.receiveCommand(worker);
            this.command(messageCommandZeroMQ);
            CommandZeroMQ.replyCommand(worker, messageCommandZeroMQ);
        }
    }

    @Override
    public abstract void command(MessageCommandZeroMQ messageCommandZeroMQ);
}
