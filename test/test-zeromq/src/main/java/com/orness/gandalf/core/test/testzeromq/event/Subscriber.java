package com.orness.gandalf.core.test.testzeromq.event;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Subscriber {

    protected ZContext context;
    protected ZMQ.Socket frontEndSubscriber;
    protected String frontEndsubScriberConnection;
    protected ZMQ.Socket backEndSubscriber;
    protected String backEndSubscriberConnection;
    protected String identity;

    public Subscriber() {
        this.context = new ZContext();
        this.frontEndSubscriber = this.context.createSocket(SocketType.SUB);
        this.backEndSubscriber = this.context.createSocket(SocketType.DEALER);
    }

    protected void init(String identity, String frontEndsubScriberConnection, String backEndSubscriberConnection) {
        this.frontEndsubScriberConnection = frontEndsubScriberConnection;
        System.out.println("SubscriberZeroMQ binding to: " + this.frontEndsubScriberConnection);
        this.frontEndSubscriber.connect(this.frontEndsubScriberConnection);
        this.identity = identity;
        this.backEndSubscriber.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.backEndSubscriberConnection = backEndSubscriberConnection;
        System.out.println("WorkerZeroMQ connect to: " + this.backEndSubscriberConnection);
        this.backEndSubscriber.bind(this.backEndSubscriberConnection);
    }

    public void close() {
        this.frontEndSubscriber.close();
        this.backEndSubscriber.close();
        this.context.close();
    }
}
