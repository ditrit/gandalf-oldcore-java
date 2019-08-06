package com.orness.gandalf.core.module.zeromqmodule.event.subscriber;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ.Socket;

public abstract class SubscriberZeroMQ {
    private String connection;
    private ZContext context;
    protected Socket subscriber;

    public SubscriberZeroMQ() {
        //this.connection = connection;
        this.context = new ZContext();
        this.subscriber = this.context.createSocket(SocketType.SUB);
        //this.connect();
    }

    protected void setConnection(String connection) {
        this.connection = connection;
    }

    protected void connect(String connection) {
        this.setConnection(connection);
        this.subscriber.connect(this.connection);
    }

    public void close() {
        this.subscriber.close();
        this.context.close();
    }
}
