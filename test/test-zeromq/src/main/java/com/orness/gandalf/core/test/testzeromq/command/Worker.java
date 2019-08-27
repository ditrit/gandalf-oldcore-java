package com.orness.gandalf.core.test.testzeromq.command;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.test.testzeromq.Constant.*;

public abstract class Worker {

    protected ZContext context;
    protected static ZMQ.Socket frontEndWorker;
    //TODO MULTIPLE
    protected String frontEndWorkerConnections; //IPC
    protected String workerServiceClass;

    protected void init(String workerServiceClass, String frontEndWorkerConnections) {
        this.context = new ZContext();
        this.workerServiceClass = workerServiceClass;
        //this.workerServiceClassType = workerServiceClassType;

        //Worker
        this.frontEndWorker = this.context.createSocket(SocketType.DEALER);
        this.frontEndWorker.setIdentity(this.workerServiceClass.getBytes(ZMQ.CHARSET));
        this.frontEndWorkerConnections = frontEndWorkerConnections;
        System.out.println("WorkerZeroMQ connect to: " + this.frontEndWorkerConnections);
        this.frontEndWorker.connect(this.frontEndWorkerConnections);

    }

    protected void close() {
        this.frontEndWorker.close();
        this.context.close();
    }

    protected void sendReadyCommand() {
        ZMsg ready = new ZMsg();
        ready.add(COMMAND_COMMAND_READY);
        ready.send(this.frontEndWorker);
        ready.destroy();
    }

    protected void sendResultCommand(ZMsg request, String result) {
        request.addFirst(COMMAND_COMMAND_RESULT);
        request.addLast(result);
        request.send(this.frontEndWorker);
    }

}
