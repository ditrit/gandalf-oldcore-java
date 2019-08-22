package com.orness.gandalf.core.test.testzeromq.event;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Publisher {

    protected ZContext context;
    protected ZMQ.Socket backEndPublisher;
    protected String backEndPublisherConnection;

    public Publisher() {
        this.context = new ZContext();
        this.backEndPublisher = this.context.createSocket(SocketType.PUB);
    }

    public void init(String backEndPublisherConnection) {
        this.backEndPublisherConnection = backEndPublisherConnection;
        System.out.println("PublisherZeroMQ binding to: " + this.backEndPublisherConnection);
        this.backEndPublisher.connect(this.backEndPublisherConnection);
    }

    public void close() {
        this.backEndPublisher.close();
        this.context.close();
    }
}
