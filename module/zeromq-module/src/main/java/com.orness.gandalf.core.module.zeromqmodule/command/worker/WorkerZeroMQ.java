package com.orness.gandalf.core.module.zeromqmodule.command.worker;

import org.zeromq.*;

import java.util.Random;

public abstract class WorkerZeroMQ {

    private static Random rand = new Random(System.nanoTime());
    private String connection;
    private ZContext context;
    protected ZMQ.Socket worker;
    protected String identity;

    public WorkerZeroMQ() {
        //this.connection = connection;
        this.context = new ZContext();
        this.worker = this.context.createSocket(SocketType.REP);
        this.identity = String.format("%04X-%04X", rand.nextInt(), rand.nextInt());
        this.worker.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        //this.connect();
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public void connect(String connection) {
        this.setConnection(connection);
        this.worker.connect(this.connection);
    }

    public void close() {
        this.worker.close();
        this.context.close();
    }
}
