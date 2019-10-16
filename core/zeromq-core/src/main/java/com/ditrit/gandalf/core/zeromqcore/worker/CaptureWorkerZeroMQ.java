package com.ditrit.gandalf.core.zeromqcore.worker;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;


public abstract class CaptureWorkerZeroMQ {

    protected ZContext context;
    //TODO MULTIPLE
    protected ZMQ.Socket frontEndWorker;
    protected String frontEndWorkerConnection; //IPC
    protected ZMQ.Socket frontEndSubscriberWorker;
    protected String frontEndSubscriberWorkerConnection; //IPC
    protected String identity;

    protected void init(String identity, String frontEndWorkerConnection, String frontEndSubscriberWorkerConnection) {
        this.context = new ZContext();
        this.identity = identity;
        //this.workerServiceClassType = workerServiceClassType;

        //Worker
        this.frontEndWorker = this.context.createSocket(SocketType.DEALER);
        this.frontEndWorker.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.frontEndWorkerConnection = frontEndWorkerConnection;
        System.out.println("WorkerZeroMQ connect to: " + this.frontEndWorkerConnection);
        this.frontEndWorker.connect(this.frontEndWorkerConnection);

        //Worker
        this.frontEndSubscriberWorker = this.context.createSocket(SocketType.SUB);
        this.frontEndSubscriberWorker.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.frontEndSubscriberWorkerConnection = frontEndSubscriberWorkerConnection;
        System.out.println("WorkerZeroMQ connect to: " + this.frontEndSubscriberWorkerConnection);
        this.frontEndSubscriberWorker.connect(this.frontEndSubscriberWorkerConnection);

    }

    protected void close() {
        this.frontEndWorker.close();
        this.frontEndSubscriberWorker.close();
        this.context.close();
    }

    protected void sendReadyCommand() {
        ZMsg ready = new ZMsg();
        ready.send(this.frontEndWorker);
        ready.destroy();
    }

    protected void sendResultCommand(ZMsg request, String result) {
        request.addLast(result);
        request.send(this.frontEndWorker);
    }

}
