package com.orness.gandalf.core.test.testzeromq.command;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.test.testzeromq.Constant.*;

public abstract class Worker {

    private ZContext context;
    protected static ZMQ.Socket frontEndWorker;
    //TODO MULTIPLE
    protected String frontEndWorkerConnections; //IPC
    protected String workerServiceClass;
    protected String workerServiceClassType;

    public Worker() {
    }

    protected void init(String workerServiceClass, String workerServiceClassType, String frontEndWorkerConnections) {
        this.context = new ZContext();
        this.workerServiceClass = workerServiceClass;
        this.workerServiceClassType = workerServiceClassType;

        //Broker
        this.frontEndWorker = this.context.createSocket(SocketType.DEALER);
        this.frontEndWorker.setIdentity(this.workerServiceClass.getBytes(ZMQ.CHARSET));
        this.frontEndWorkerConnections = frontEndWorkerConnections;
        System.out.println("WorkerZeroMQ connect to: " + frontEndWorkerConnections);
        this.frontEndWorker.connect(frontEndWorkerConnections);
    }

    protected void close() {
        this.frontEndWorker.close();
        this.context.close();
    }

    protected void reconnectToRoutingWorker() {
        if (this.frontEndWorker != null) {
            this.context.destroySocket(frontEndWorker);
        }
        this.init(this.workerServiceClass, this.workerServiceClassType, this.frontEndWorkerConnections);

        // Register service with broker
        this.sendReadyCommand();
    }

    protected void sendReadyCommand() {
        this.frontEndWorker.sendMore(COMMAND_COMMAND_READY);
        this.frontEndWorker.send(this.workerServiceClass);
    }

    protected void sendResultCommand(ZMsg request, String result) {
        request.addFirst(COMMAND_COMMAND_RESULT);
        request.addLast(result);
        request.send(this.frontEndWorker);
        this.sendReadyCommand();
    }

    protected ZMsg receiveCommand() {
        return ZMsg.recvMsg(this.frontEndWorker);
    }
}
