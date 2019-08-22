package com.orness.gandalf.core.test.testzeromq.command;

import org.zeromq.*;

import static com.orness.gandalf.core.test.testzeromq.Constant.*;

public abstract class Worker {

    protected ZContext context;
    protected static ZMQ.Socket frontEndWorker;
    protected String[] frontEndWorkerConnections;
    protected static ZMQ.Socket backEndWorker;
    protected String backEndWorkerConnection; //IPC
    protected String service;

    public Worker() {
    }

    protected void init(String service, String[] frontEndWorkerConnections, String backEndWorkerConnection) {
        this.context = new ZContext();
        this.service = service;

        this.frontEndWorker = this.context.createSocket(SocketType.DEALER);
        this.frontEndWorker.setIdentity(this.service.getBytes(ZMQ.CHARSET));
        this.frontEndWorkerConnections = frontEndWorkerConnections;
        for(String connection : this.frontEndWorkerConnections) {
            System.out.println("WorkerZeroMQ connect to: " + connection);
            this.frontEndWorker.connect(connection);
        }

        this.backEndWorker = this.context.createSocket(SocketType.DEALER);
        this.backEndWorker.setIdentity(this.service.getBytes(ZMQ.CHARSET));
        this.backEndWorkerConnection = backEndWorkerConnection;
        System.out.println("WorkerZeroMQ connect to: " + this.backEndWorkerConnection);
        this.backEndWorker.bind(this.backEndWorkerConnection);
    }

    protected void close() {
        this.frontEndWorker.close();
        this.backEndWorker.close();
        this.context.close();
    }

    protected void reconnectToBroker() {
        if (this.backEndWorker != null) {
            this.context.destroySocket(backEndWorker);
        }
        this.init(this.service, this.frontEndWorkerConnections, this.backEndWorkerConnection);

        // Register service with broker
        this.sendReadyCommand();
    }

    protected void sendReadyCommand() {
        this.frontEndWorker.sendMore(WORKER_COMMAND_READY);
        this.frontEndWorker.send(this.service);

    }
}