package com.orness.gandalf.core.test.testzeromq.command;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.test.testzeromq.Constant.*;

public abstract class Command {

    private ZContext context;
    protected static ZMQ.Socket commandSocket;
    protected String commandConnections; //IPC
    protected String command;

    public Command() {
    }

    protected void init(String command, String commandConnections) {
        this.context = new ZContext();
        this.command = command;
        this.commandSocket = this.context.createSocket(SocketType.DEALER);
        this.commandSocket.setIdentity(this.command.getBytes(ZMQ.CHARSET));
        this.commandConnections = commandConnections;
        System.out.println("WorkerZeroMQ connect to: " + commandConnections);
        this.commandSocket.connect(commandConnections);
    }

    protected void close() {
        this.commandSocket.close();
        this.context.close();
    }

    protected void reconnectToWorker() {
        if (this.commandSocket != null) {
            this.context.destroySocket(commandSocket);
        }
        this.init(this.command, this.commandConnections);

        // Register service with broker
        this.sendReadyCommand();
    }

    protected void sendReadyCommand() {
        this.commandSocket.send(COMMAND_COMMAND_READY);
    }

    protected void sendResultCommand(ZMsg request, String result) {
        request.add(result);
        request.send(this.commandSocket);

        this.sendReadyCommand();
    }

    protected ZMsg receiveCommand() {
        return ZMsg.recvMsg(this.commandSocket);
    }
}
