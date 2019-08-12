package com.orness.gandalf.core.module.zeromqmodule.command.worker;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.CommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;

public abstract class RunnableWorkerZeroMQ extends WorkerZeroMQ implements Runnable {

    protected Gson mapper;

    public RunnableWorkerZeroMQ() {
        super();
        mapper = new Gson();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            MessageCommandZeroMQ messageCommandZeroMQ = CommandZeroMQ.receiveCommand(this.worker);
            this.command(messageCommandZeroMQ);
            CommandZeroMQ.replyCommand(this.worker, messageCommandZeroMQ);
        }
    }

    public abstract Object parse(String messageContent);

    public abstract void command(MessageCommandZeroMQ messageCommandZeroMQ);
}
