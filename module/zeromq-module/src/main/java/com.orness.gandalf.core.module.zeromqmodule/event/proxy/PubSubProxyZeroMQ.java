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

        this.context = new ZContext();

        // Publisher EndPoint
        this.frontend = this.context.createSocket(SocketType.XSUB);
        System.out.println("ProxyPublisherZeroMQ binding to: " + publisherConnection);
        this.frontend.bind(this.publisherConnection);

        // Subscriber EndPoint
        this.backend = this.context.createSocket(SocketType.XPUB);
        System.out.println("ProxySubscriberZeroMQ binding to: " + this.subscriberConnection);
        this.backend.bind(this.subscriberConnection);

        // Run the proxy
        ZMQ.proxy(this.frontend, this.backend, null);

        this.close();
    }

    public void close() {
        this.frontend.close();
        this.backend.close();
        this.context.destroy();
    }

}