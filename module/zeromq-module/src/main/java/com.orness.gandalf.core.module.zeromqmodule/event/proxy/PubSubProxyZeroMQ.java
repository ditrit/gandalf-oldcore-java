package com.orness.gandalf.core.module.zeromqmodule.event.proxy;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class PubSubProxyZeroMQ {

    public static Socket frontend;
    private String subscriberConnection;
    public static Socket backend;
    private String publisherConnection;
    private ZContext context;

    public PubSubProxyZeroMQ(String subscriberConnection, String publisherConnection) {
        this.publisherConnection = publisherConnection;
        this.subscriberConnection = subscriberConnection;
        this.open();
    }

    public void open() {

        context = new ZContext();

        // Publisher EndPoint
        frontend = context.createSocket(SocketType.XSUB);
        System.out.println("PublisherZeroMQ binding to: " + publisherConnection);
        frontend.bind(publisherConnection);

        // Subscriber EndPoint
        backend = context.createSocket(SocketType.XPUB);
        System.out.println("SubscriberZeroMQ binding to: " + subscriberConnection);
        backend.bind(subscriberConnection);

        // Run the proxy
        ZMQ.proxy(frontend, backend, null);

        this.close();
    }

    public void close() {
        frontend.close();
        backend.close();
        context.destroy();
    }

}