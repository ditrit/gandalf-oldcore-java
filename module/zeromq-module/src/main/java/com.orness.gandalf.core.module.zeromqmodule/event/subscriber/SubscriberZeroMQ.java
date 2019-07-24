package com.orness.gandalf.core.module.zeromqmodule.event.subscriber;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ.Socket;

public abstract class SubscriberZeroMQ {
    private String connection;
    private ZContext context;
    protected Socket subscriber;

    public SubscriberZeroMQ(String connection) {
        this.connection = connection;
        this.context = new ZContext();
        this.subscriber = this.context.createSocket(SocketType.SUB);
        this.connect();
    }

    public void connect() {
        this.subscriber.connect(this.connection);
    }

    public void close() {
        this.subscriber.close();
        this.context.close();
    }
}
