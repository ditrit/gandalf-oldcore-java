package com.orness.gandalf.core.test.testzeromq.command;

import org.zeromq.*;

import static com.orness.gandalf.core.test.testzeromq.Constant.*;

public abstract class Worker {

    protected ZContext context;
    protected static ZMQ.Socket worker;
    protected String[] workerConnections;
    protected static ZMQ.Socket command;
    protected String commandConnections; //IPC
    protected String service;

    public Worker() {
    }

    protected void init(String service, String[] workerConnections) {
        this.context = new ZContext();
        this.service = service;

        this.worker = this.context.createSocket(SocketType.DEALER);
        this.worker.setIdentity(this.service.getBytes(ZMQ.CHARSET));
        this.workerConnections = workerConnections;
        for(String connection : this.workerConnections) {
            System.out.println("WorkerZeroMQ connect to: " + connection);
            this.worker.connect(connection);
        }

        this.command = this.context.createSocket(SocketType.DEALER);
        this.command.setIdentity(this.service.getBytes(ZMQ.CHARSET));
        System.out.println("WorkerZeroMQ connect to: " + this.commandConnections);
        this.command.bind(this.commandConnections);
    }

    protected void close() {
        this.worker.close();
        this.command.close();
        this.context.close();
    }

    protected void reconnectToBroker() {
        if (this.worker != null) {
            this.context.destroySocket(worker);
        }
        this.init(this.service, this.workerConnections);

        // Register service with broker
        this.sendReadyCommand();
    }

    protected void sendReadyCommand() {
        this.worker.send(WORKER_COMMAND_READY);
    }
}