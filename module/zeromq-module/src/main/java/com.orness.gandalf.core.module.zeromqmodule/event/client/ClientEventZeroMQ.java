package com.orness.gandalf.core.module.zeromqmodule.event.client;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Random;

public abstract class ClientEventZeroMQ {

    private static Random rand = new Random(System.nanoTime());
    private String connection;
    private ZContext context;
    protected ZMQ.Socket client;
    protected String identity;

    protected void setConnection(String connection) {
        this.connection = connection;
    }

    public ClientEventZeroMQ() {
        //this.connection = connection;
        this.context = new ZContext();
        this.client = this.context.createSocket(SocketType.REQ);
        this.identity = String.format("%04X-%04X", rand.nextInt(), rand.nextInt());
        this.client.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        //this.connect();
    }

    public void bind(String connection) {
        this.setConnection(connection);
        System.out.println("ClientZeroMQ connect to: " + this.connection);
        this.client.bind(this.connection);
    }

    public void close() {
        this.client.close();
        this.context.close();
    }
}
