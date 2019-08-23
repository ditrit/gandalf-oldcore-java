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
    public static ZMQ.Socket backEndEventCapture;
    private String backEndCaptureEventConnection;

    public Proxy(String frontEndEventConnection, String backEndEventConnection, String backEndCaptureEventConnection) {
        this.frontEndEventConnection = frontEndEventConnection;
        this.backEndEventConnection = backEndEventConnection;
        this.backEndCaptureEventConnection = backEndCaptureEventConnection;
        this.open();
    }

    public void open() {
        this.context = new ZContext();

        // Publisher
        this.frontEndEvent = this.context.createSocket(SocketType.XSUB);
        System.out.println("ProxyPublisherZeroMQ binding to frontEndEventConnection: " + frontEndEventConnection);
        this.frontEndEvent.bind(this.frontEndEventConnection);
        // Subscriber
        this.backEndEvent = this.context.createSocket(SocketType.XPUB);
        System.out.println("ProxySubscriberZeroMQ binding to backEndEventConnection: " + this.backEndEventConnection);
        this.backEndEvent.bind(this.backEndEventConnection);
        //Capture
        this.backEndEventCapture = this.context.createSocket(SocketType.DEALER);
        System.out.println("BrokerCaptureZeroMQ binding to backEndCaptureEventConnection: " + this.backEndCaptureEventConnection);
        this.backEndEventCapture.bind(this.backEndCaptureEventConnection);
        // Run the proxy
        ZMQ.proxy(this.frontEndEvent, this.backEndEvent,  this.backEndEventCapture);

        this.close();
    }

    public void close() {
        this.frontEndEvent.close();
        this.backEndEvent.close();
        this.context.destroy();
    }

}