package com.orness.gandalf.core.test.testzeromq.command;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.test.testzeromq.Constant.*;

public abstract class Command {

    private ZContext context;
    protected static ZMQ.Socket frontEndCommand;
    protected String frontEndCommandConnections; //IPC
    protected String command;

    public Command() {
    }

    protected void init(String command, String frontEndCommandConnections) {
        this.context = new ZContext();
        this.command = command;
        this.frontEndCommand = this.context.createSocket(SocketType.DEALER);
        this.frontEndCommand.setIdentity(this.command.getBytes(ZMQ.CHARSET));
        this.frontEndCommandConnections = frontEndCommandConnections;
        System.out.println("WorkerZeroMQ connect to: " + frontEndCommandConnections);
        this.frontEndCommand.connect(frontEndCommandConnections);
    }

    protected void close() {
        this.frontEndCommand.close();
        this.context.close();
    }

    protected void reconnectToWorker() {
        if (this.frontEndCommand != null) {
            this.context.destroySocket(frontEndCommand);
        }
        this.init(this.command, this.frontEndCommandConnections);

        // Register service with broker
        this.sendReadyCommand();
    }

    protected void sendReadyCommand() {
        this.frontEndCommand.send(COMMAND_COMMAND_READY);
    }

    protected void sendResultCommand(ZMsg request, String result) {
        request.add(result);
        request.send(this.frontEndCommand);

        this.sendReadyCommand();
    }

    protected ZMsg receiveCommand() {
        return ZMsg.recvMsg(this.frontEndCommand);
    }
}
