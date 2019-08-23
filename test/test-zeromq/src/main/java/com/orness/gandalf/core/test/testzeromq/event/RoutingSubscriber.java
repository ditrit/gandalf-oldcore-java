package com.orness.gandalf.core.test.testzeromq.event;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class RoutingSubscriber {

    protected ZContext context;
    protected ZMQ.Socket frontEndRoutingSubscriber;
    protected String frontEndRoutingSubscriberConnection;
    protected ZMQ.Socket backEndRoutingSubscriber;
    protected String backEndRoutingSubscriberConnection;
    protected String routingSubscriberConnector;

    public RoutingSubscriber() {

    }

    protected void init(String routingSubscriberConnector, String frontEndRoutingSubscriberConnection, String backEndSubscriberConnection) {
        this.context = new ZContext();
        this.routingSubscriberConnector = routingSubscriberConnector;

        this.frontEndRoutingSubscriberConnection = frontEndRoutingSubscriberConnection;
        this.frontEndRoutingSubscriber = this.context.createSocket(SocketType.SUB);
        System.out.println("SubscriberZeroMQ binding to: " + this.frontEndRoutingSubscriberConnection);
        this.frontEndRoutingSubscriber.connect(this.frontEndRoutingSubscriberConnection);

        this.backEndRoutingSubscriber = this.context.createSocket(SocketType.ROUTER);
        this.backEndRoutingSubscriber.setIdentity(this.routingSubscriberConnector.getBytes(ZMQ.CHARSET));
        this.backEndRoutingSubscriberConnection = backEndSubscriberConnection;
        System.out.println("WorkerZeroMQ connect to: " + this.backEndRoutingSubscriberConnection);
        this.backEndRoutingSubscriber.bind(this.backEndRoutingSubscriberConnection);
    }

    public void close() {
        this.frontEndRoutingSubscriber.close();
        this.backEndRoutingSubscriber.close();
        this.context.close();
    }

    protected void reconnectToProxy() {
        if (this.frontEndRoutingSubscriberConnection != null) {
            this.context.destroySocket(frontEndRoutingSubscriber);
        }
        this.init(this.routingSubscriberConnector, this.frontEndRoutingSubscriberConnection, this.backEndRoutingSubscriberConnection);
    }
}
