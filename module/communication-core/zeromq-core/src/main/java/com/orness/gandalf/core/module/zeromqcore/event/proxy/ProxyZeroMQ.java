package com.orness.gandalf.core.module.zeromqcore.event.proxy;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class ProxyZeroMQ {

    protected static Socket frontEndEvent;
    private String frontEndEventConnection;
    protected static Socket backEndEvent;
    private String backEndEventConnection;
    private ZContext context;
    protected static Socket backEndEventCapture;
    private String backEndCaptureEventConnection;

    public ProxyZeroMQ(String frontEndEventConnection, String backEndEventConnection, String backEndCaptureEventConnection) {
        this.frontEndEventConnection = frontEndEventConnection;
        this.backEndEventConnection = backEndEventConnection;
        this.backEndCaptureEventConnection = backEndCaptureEventConnection;
        this.open();
    }

    protected void open() {
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
        //TODO
      /*  this.backEndEventCapture = this.context.createSocket(SocketType.DEALER);
        System.out.println("BrokerCaptureZeroMQ binding to backEndCaptureEventConnection: " + this.backEndCaptureEventConnection);
        this.backEndEventCapture.bind(this.backEndCaptureEventConnection);*/
        // Run the proxy
        //ZMQ.proxy(this.frontEndEvent, this.backEndEvent,  this.backEndEventCapture);
        ZMQ.proxy(this.frontEndEvent, this.backEndEvent,  null);

        this.close();
    }

    public void close() {
        this.frontEndEvent.close();
        this.backEndEvent.close();
        this.context.destroy();
    }

}