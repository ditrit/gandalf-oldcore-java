package com.ditrit.gandalf.core.zeromqcore.event.aggregator;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public abstract class AggregatorSubscriberZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket frontEndSendRoutingSubscriber;
    protected String frontEndSendRoutingSubscriberConnection;
    protected ZMQ.Socket frontEndReceiveRoutingSubscriber;
    protected String frontEndReceiveRoutingSubscriberConnection;
    protected ZMQ.Socket backEndSendRoutingSubscriber;
    protected String backEndSendRoutingSubscriberConnection;
    protected ZMQ.Socket backEndReceiveRoutingSubscriber;
    protected String backEndReceiveRoutingSubscriberConnection;
    protected String routingSubscriberConnector;

    protected void init(String routingSubscriberConnector, String frontEndSendRoutingSubscriberConnection, String backEndSendRoutingSubscriberConnection, String frontEndReceiveRoutingSubscriberConnection, String backEndReceiveRoutingSubscriberConnection) {
        this.context = new ZContext();
        this.routingSubscriberConnector = routingSubscriberConnector;

        //Send Proxy
        this.frontEndSendRoutingSubscriber = this.context.createSocket(SocketType.XPUB);
        this.frontEndSendRoutingSubscriberConnection = frontEndSendRoutingSubscriberConnection;
        System.out.println("RoutingSubscriberZeroMQ connect to frontEndSendRoutingSubscriberConnection: " + this.frontEndSendRoutingSubscriberConnection);
        this.frontEndSendRoutingSubscriber.connect(this.frontEndSendRoutingSubscriberConnection);

        //Receive Proxy
        this.frontEndReceiveRoutingSubscriber = this.context.createSocket(SocketType.XSUB);
        this.frontEndReceiveRoutingSubscriberConnection = frontEndReceiveRoutingSubscriberConnection;
        System.out.println("RoutingSubscriberZeroMQ connect to frontEndReceiveRoutingSubscriberConnection: " + this.frontEndReceiveRoutingSubscriberConnection);
        this.frontEndReceiveRoutingSubscriber.connect(this.frontEndReceiveRoutingSubscriberConnection);

        //Send Worker
        this.backEndSendRoutingSubscriber = this.context.createSocket(SocketType.SUB);
        this.backEndSendRoutingSubscriber.setIdentity(this.routingSubscriberConnector.getBytes(ZMQ.CHARSET));
        this.backEndSendRoutingSubscriberConnection = backEndSendRoutingSubscriberConnection;
        System.out.println("RoutingSubscriberZeroMQ binding to backEndSendRoutingSubscriberConnection: " + this.backEndSendRoutingSubscriberConnection);
        this.backEndSendRoutingSubscriber.bind(this.backEndSendRoutingSubscriberConnection);
        this.backEndSendRoutingSubscriber.subscribe(ZMQ.SUBSCRIPTION_ALL);

        //Receive Worker
        this.backEndReceiveRoutingSubscriber = this.context.createSocket(SocketType.XPUB);
        this.backEndReceiveRoutingSubscriber.setIdentity(this.routingSubscriberConnector.getBytes(ZMQ.CHARSET));
        this.backEndReceiveRoutingSubscriberConnection = backEndReceiveRoutingSubscriberConnection;
        System.out.println("RoutingSubscriberZeroMQ binding to backEndReceiveRoutingSubscriberConnection: " + this.backEndReceiveRoutingSubscriberConnection);
        this.backEndReceiveRoutingSubscriber.bind(this.backEndReceiveRoutingSubscriberConnection);
    }

    public void close() {
        this.frontEndSendRoutingSubscriber.close();
        this.backEndSendRoutingSubscriber.close();
        this.frontEndReceiveRoutingSubscriber.close();
        this.backEndReceiveRoutingSubscriber.close();
        this.context.close();
    }

    protected void reconnectToProxy() {
        if (this.frontEndReceiveRoutingSubscriberConnection != null) {
            this.context.destroySocket(frontEndReceiveRoutingSubscriber);
        }
        this.init(this.routingSubscriberConnector, this.frontEndSendRoutingSubscriberConnection, this.backEndSendRoutingSubscriberConnection , this.frontEndReceiveRoutingSubscriberConnection, this.backEndReceiveRoutingSubscriberConnection);
    }
}
