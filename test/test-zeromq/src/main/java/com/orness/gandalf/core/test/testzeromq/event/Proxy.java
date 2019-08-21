package com.orness.gandalf.core.test.testzeromq.event;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class Proxy {

    public static Socket frontEndEvent;
    private String frontEndEventConnection;
    public static Socket backEndEvent;
    private String backEndEventConnection;
    private ZContext context;

    public Proxy(String frontEndEventConnection, String backEndEventConnection) {
        this.frontEndEventConnection = frontEndEventConnection;
        this.backEndEventConnection = backEndEventConnection;
        this.open();
    }

    public void open() {

        this.context = new ZContext();

        // Publisher EndPoint
        this.frontEndEvent = this.context.createSocket(SocketType.XPUB);
        System.out.println("ProxyPublisherZeroMQ binding to: " + frontEndEventConnection);
        this.frontEndEvent.bind(this.frontEndEventConnection);

        // Subscriber EndPoint
        this.backEndEvent = this.context.createSocket(SocketType.XPUB);
        System.out.println("ProxySubscriberZeroMQ binding to: " + this.backEndEventConnection);
        this.backEndEvent.bind(this.backEndEventConnection);

        // Run the proxy
        ZMQ.proxy(this.frontEndEvent, this.backEndEvent, null);

        this.close();
    }

    public void close() {
        this.frontEndEvent.close();
        this.backEndEvent.close();
        this.context.destroy();
    }

}