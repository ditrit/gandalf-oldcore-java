package com.ditrit.gandalf.core.zeromqcore.event.client;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.sql.Timestamp;

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

    public void sendEvent(String topic, String event, String timeout, String payload) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        this.backEndPublisher.sendMore(topic);
        this.backEndPublisher.sendMore(event);
        this.backEndPublisher.sendMore(timeout);
        this.backEndPublisher.sendMore(timestamp.toString());
        this.backEndPublisher.send(payload);
    }
}