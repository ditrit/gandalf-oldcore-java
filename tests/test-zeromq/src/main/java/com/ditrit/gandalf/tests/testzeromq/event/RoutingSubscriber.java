package com.ditrit.gandalf.tests.testzeromq.event;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import static com.ditrit.gandalf.tests.testzeromq.Constant.ROUTING_SUBSCRIBER;

public class RoutingSubscriber {

    protected static final String ROUTING_TYPE = ROUTING_SUBSCRIBER;

    protected ZContext context;
    protected ZMQ.Socket frontEndRoutingSubscriber;
    protected String frontEndRoutingSubscriberConnection;
    protected ZMQ.Socket backEndRoutingSubscriber;
    protected String backEndRoutingSubscriberConnection;
    protected String routingSubscriberConnector;

    protected void init(String routingSubscriberConnector, String frontEndRoutingSubscriberConnection, String backEndSubscriberConnection) {
        this.context = new ZContext();
        this.routingSubscriberConnector = routingSubscriberConnector;
        //Proxy
        this.frontEndRoutingSubscriber = this.context.createSocket(SocketType.SUB);
        this.frontEndRoutingSubscriberConnection = frontEndRoutingSubscriberConnection;
        System.out.println("SubscriberZeroMQ connect to frontEndRoutingSubscriberConnection: " + this.frontEndRoutingSubscriberConnection);
        this.frontEndRoutingSubscriber.connect(this.frontEndRoutingSubscriberConnection);
        //Worker
        this.backEndRoutingSubscriber = this.context.createSocket(SocketType.XPUB);
        this.backEndRoutingSubscriber.setIdentity(this.routingSubscriberConnector.getBytes(ZMQ.CHARSET));
        this.backEndRoutingSubscriberConnection = backEndSubscriberConnection;
        System.out.println("WorkerZeroMQ binding to backEndRoutingSubscriberConnection: " + this.backEndRoutingSubscriberConnection);
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
