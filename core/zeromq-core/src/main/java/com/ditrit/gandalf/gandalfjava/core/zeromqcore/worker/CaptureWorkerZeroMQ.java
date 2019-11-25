package com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;


public abstract class CaptureWorkerZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket frontEndWorker;
    protected String frontEndWorkerConnection;
    protected ZMQ.Socket frontEndSubscriberWorker;
    protected String frontEndSubscriberWorkerConnection;
    protected String identity;

    protected void init(String identity, String frontEndWorkerConnection, String frontEndSubscriberWorkerConnection) {
        this.context = new ZContext();
        this.identity = identity;

        this.frontEndWorker = this.context.createSocket(SocketType.DEALER);
        this.frontEndWorker.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.frontEndWorkerConnection = frontEndWorkerConnection;
        System.out.println("WorkerZeroMQ connect to: " + this.frontEndWorkerConnection);
        this.frontEndWorker.connect(this.frontEndWorkerConnection);

        this.frontEndSubscriberWorker = this.context.createSocket(SocketType.SUB);
        this.frontEndSubscriberWorker.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.frontEndSubscriberWorkerConnection = frontEndSubscriberWorkerConnection;
        System.out.println("WorkerZeroMQ connect to: " + this.frontEndSubscriberWorkerConnection);
        this.frontEndSubscriberWorker.connect(this.frontEndSubscriberWorkerConnection);
        this.frontEndSubscriberWorker.subscribe(ZMQ.SUBSCRIPTION_ALL);
    }

    protected void close() {
        this.frontEndWorker.close();
        this.frontEndSubscriberWorker.close();
        this.context.close();
    }
}

