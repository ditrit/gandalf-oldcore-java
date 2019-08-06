package com.orness.gandalf.core.module.zeromqmodule.command.client;

import org.zeromq.*;

import java.util.Random;

public abstract class ClientZeroMQ {

    private static Random rand = new Random(System.nanoTime());
    private String connection;
    private ZContext context;
    protected ZMQ.Socket client;
    protected String identity;

    protected void setConnection(String connection) {
        this.connection = connection;
    }

    public ClientZeroMQ() {
        //this.connection = connection;
        this.context = new ZContext();
        this.client = this.context.createSocket(SocketType.REQ);
        this.identity = String.format("%04X-%04X", rand.nextInt(), rand.nextInt());
        this.client.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        //this.connect();
    }

    public void connect(String connection) {
        this.setConnection(connection);
        this.client.connect(this.connection);
    }

    public void close() {
        this.client.close();
        this.context.close();
    }
}
