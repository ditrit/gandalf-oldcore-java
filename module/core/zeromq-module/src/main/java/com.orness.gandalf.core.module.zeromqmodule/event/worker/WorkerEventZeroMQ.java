package com.orness.gandalf.core.module.zeromqmodule.event.worker;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Random;

public abstract class WorkerEventZeroMQ {

    private static Random rand = new Random(System.nanoTime());
    protected String connection;
    private ZContext context;
    protected ZMQ.Socket worker;
    protected String identity;

    public WorkerEventZeroMQ() {
        //this.connection = connection;
        this.context = new ZContext();
        this.worker = this.context.createSocket(SocketType.REP);
        this.identity = String.format("%04X-%04X", rand.nextInt(), rand.nextInt());
        this.worker.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        //this.connect();
    }

    public void connect(String connection) {
        this.connection = connection;
        System.out.println("WorkerZeroMQ bind to: " + this.connection);
        this.worker.connect(this.connection);
    }

    public void close() {
        this.worker.close();
        this.context.close();
    }
}
