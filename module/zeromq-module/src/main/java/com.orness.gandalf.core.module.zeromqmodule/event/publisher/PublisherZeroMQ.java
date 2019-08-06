package com.orness.gandalf.core.module.zeromqmodule.event.publisher;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ.Socket;

public abstract class PublisherZeroMQ {

    private ZContext context;
    private String connection;
    protected Socket publisher;

    public PublisherZeroMQ() {
        //this.connection = connection;
        this.context = new ZContext();
        this.publisher = this.context.createSocket(SocketType.PUB);
        //this.connect();
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public void connect(String connection) {
        this.setConnection(connection);
        this.publisher.connect(this.connection);
    }

    public void close() {
        this.publisher.close();
        this.context.close();
    }
}
