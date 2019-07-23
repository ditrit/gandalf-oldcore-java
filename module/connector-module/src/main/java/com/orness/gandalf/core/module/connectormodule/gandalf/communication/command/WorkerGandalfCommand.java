package com.orness.gandalf.core.module.connectormodule.gandalf.communication.command;

import com.orness.gandalf.core.module.zeromqmodule.command.domain.CommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.WorkerZeroMQ;
import org.springframework.beans.factory.annotation.Value;

import static com.orness.gandalf.core.module.constantmodule.communication.CommunicationConstant.*;

public class WorkerGandalfCommand extends WorkerZeroMQ implements Runnable {

    public WorkerGandalfCommand(@Value("${gandalf.communication.worker}") String connection) {
        super(connection);
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            MessageCommandZeroMQ messageCommandZeroMQ = CommandZeroMQ.receiveCommand(worker);
            this.command(messageCommandZeroMQ);
            CommandZeroMQ.replyCommand(worker, messageCommandZeroMQ);
        }
    }

    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

        System.out.println("ID " + this.identity);
        System.out.println("SENDER " + messageCommandZeroMQ.getSender());
        System.out.println("RECEIVER " + messageCommandZeroMQ.getReceiver());
        System.out.println("TYPE_COMMAND " + messageCommandZeroMQ.getTypeCommand());
        System.out.println("COMMAND " + messageCommandZeroMQ.getCommand());

        switch(messageCommandZeroMQ.getCommand().toString()) {
            case COMMAND_START:
                //
                break;
            case COMMAND_STOP:
                //connectorBusManager.deleteTopic(content);
                break;
            case COMMAND_SUBSCRIBE:
                //connectorBusManager.createTopic(content);
                break;
            case COMMAND_UNSUBSCRIBE:
                //connectorBusManager.createTopic(content);
                break;
            default:
                //DO NOTHING
                break;
        }
    }
}
