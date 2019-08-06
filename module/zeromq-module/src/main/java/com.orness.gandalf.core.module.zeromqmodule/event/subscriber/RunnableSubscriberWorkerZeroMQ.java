package com.orness.gandalf.core.module.zeromqmodule.event.subscriber;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.messagemodule.gandalf.domain.GandalfEvent;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.CommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.WorkerZeroMQ;

public abstract class RunnableSubscriberWorkerZeroMQ extends WorkerZeroMQ implements Runnable {

    protected Gson mapper;

    public RunnableSubscriberWorkerZeroMQ() {
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

    protected GandalfEvent parse(String messageContent) {
        return this.mapper.fromJson(messageContent, GandalfEvent.class);
    }

    public abstract void command(MessageCommandZeroMQ messageCommandZeroMQ);
}
