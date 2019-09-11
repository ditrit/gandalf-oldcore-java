package com.orness.gandalf.core.module.zeromqcore.event.client;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import static com.orness.gandalf.core.module.zeromqcore.constant.Constant.COMMAND_CLIENT_SEND;
import static com.orness.gandalf.core.module.zeromqcore.constant.Constant.EVENT_CLIENT_SEND;

public class PublisherZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket backEndPublisher;
    protected String backEndPublisherConnection;
    protected String identity;

    public PublisherZeroMQ() {

    }

    public PublisherZeroMQ(String identity, String backEndPublisherConnection) {
        this.init(identity, backEndPublisherConnection);
    }

    protected void init(String identity, String backEndPublisherConnection) {
        this.context = new ZContext();
        this.identity = identity;
        //Proxy
        this.backEndPublisher = this.context.createSocket(SocketType.PUB);
        this.backEndPublisher.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.backEndPublisherConnection = backEndPublisherConnection;
        System.out.println("PublisherZeroMQ connect to: " + this.backEndPublisherConnection);
        this.backEndPublisher.connect(this.backEndPublisherConnection);
    }

    public void close() {
        this.backEndPublisher.close();
        this.context.close();
    }

    public void sendEvent(String topic, String event, String payload) {
        this.backEndPublisher.sendMore(topic);
        this.backEndPublisher.sendMore(EVENT_CLIENT_SEND);
        this.backEndPublisher.sendMore(event);
        this.backEndPublisher.send(payload);
    }
}