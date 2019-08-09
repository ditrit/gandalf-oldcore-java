package com.orness.gandalf.core.module.zeromqmodule.event.worker;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.CommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.WorkerZeroMQ;

public abstract class RunnableWorkerEventZeroMQ extends WorkerEventZeroMQ implements Runnable {

    protected Gson mapper;

    public RunnableWorkerEventZeroMQ() {
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
