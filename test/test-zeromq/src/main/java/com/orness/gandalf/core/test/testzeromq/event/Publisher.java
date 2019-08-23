package com.orness.gandalf.core.test.testzeromq.event;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Publisher {

    protected ZContext context;
    protected ZMQ.Socket backEndPublisher;
    protected String backEndPublisherConnection;
    protected String identity;

    public Publisher() {

    }

    public void init(String identity, String backEndPublisherConnection) {
        this.context = new ZContext();
        this.identity = identity;
        this.backEndPublisherConnection = backEndPublisherConnection;

        this.backEndPublisher = this.context.createSocket(SocketType.PUB);
        this.backEndPublisher.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        System.out.println("PublisherZeroMQ binding to: " + this.backEndPublisherConnection);
        this.backEndPublisher.connect(this.backEndPublisherConnection);
    }

    public void close() {
        this.backEndPublisher.close();
        this.context.close();
    }
}
