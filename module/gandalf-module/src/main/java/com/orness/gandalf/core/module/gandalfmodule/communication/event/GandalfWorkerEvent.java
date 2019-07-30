package com.orness.gandalf.core.module.gandalfmodule.communication.event;

import com.orness.gandalf.core.module.zeromqmodule.command.domain.CommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.subscriber.RunnableSubscriberWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Value;

//TODO USELESS ?
public class GandalfWorkerEvent extends RunnableSubscriberWorkerZeroMQ {

    public GandalfWorkerEvent(@Value("${gandalf.communication.worker}") String connection) {
        super(connection);
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            MessageCommandZeroMQ messageCommandZeroMQ = CommandZeroMQ.receiveCommand(this.worker);
            this.command(messageCommandZeroMQ);
            CommandZeroMQ.replyCommand(this.worker, messageCommandZeroMQ);
        }
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

        System.out.println("ID " + this.identity);
        System.out.println("SENDER " + messageCommandZeroMQ.getSender());
        System.out.println("RECEIVER " + messageCommandZeroMQ.getReceiver());
        System.out.println("TYPE_COMMAND " + messageCommandZeroMQ.getTypeCommand());
        System.out.println("COMMAND " + messageCommandZeroMQ.getCommand());

    }
}
