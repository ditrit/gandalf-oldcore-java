package com.orness.gandalf.core.module.zeromqmodule.event.publisher;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ.Socket;

public abstract class PublisherZeroMQ {

    private ZContext context;
    private String connection;
    protected Socket publisher;

    public PublisherZeroMQ(String connection) {
        this.connection = connection;
        this.open();
    }

    public void open() {
        context = new ZContext();
        publisher = context.createSocket(SocketType.PUB);
        publisher.connect(connection);
    }

    public void close() {
        publisher.close();
        context.close();
    }

   /* public void sendTopic(String topic) {
        this.publisher.sendMore(topic);

    }

    public void sendMessage(String message) {
        this.publisher.send(message);
    }*/
}
