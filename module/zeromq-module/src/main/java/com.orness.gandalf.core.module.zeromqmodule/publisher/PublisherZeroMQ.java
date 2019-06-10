package com.orness.gandalf.core.module.zeromqmodule.publisher;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ.Socket;

public class PublisherZeroMQ {

    private ZContext context;
    private Socket publisher;
    private String connection;

    public PublisherZeroMQ(String connection) {
        System.out.println("CONST PUBLISH");
        System.out.println("CONN " + connection);
        this.connection = connection;
        this.open();
    }

    public Socket getPublisher() {
        return publisher;
    }

    public void open() {
        System.out.println("PUBLISH OPEN");
        context = new ZContext();
        publisher = context.createSocket(SocketType.PUB);
        //publisher.bind(connection);
        publisher.connect(connection);
    }

    public void close() {
        publisher.close();
        context.close();
    }
}
