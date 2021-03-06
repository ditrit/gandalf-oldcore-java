package com.ditrit.gandalf.core.zeromqcore.event.aggregator;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.List;

public abstract class AggregatorSubscriberZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket frontEndSendRoutingSubscriber;
    protected String frontEndSendRoutingSubscriberConnection;
    protected ZMQ.Socket frontEndReceiveRoutingSubscriber;
    protected List<String> frontEndReceiveRoutingSubscriberConnection;
    protected ZMQ.Socket backEndSendRoutingSubscriber;
    protected String backEndSendRoutingSubscriberConnection;
    protected ZMQ.Socket backEndReceiveRoutingSubscriber;
    protected String backEndReceiveRoutingSubscriberConnection;
    protected String routingSubscriberConnector;

    protected void init(String routingSubscriberConnector, String frontEndSendRoutingSubscriberConnection, String backEndSendRoutingSubscriberConnection, List<String> frontEndReceiveRoutingSubscriberConnection, String backEndReceiveRoutingSubscriberConnection) {
        this.context = new ZContext();
        this.routingSubscriberConnector = routingSubscriberConnector;

        System.out.println("IDENTITY");
        System.out.println(routingSubscriberConnector);

        //Send Proxy
        this.frontEndSendRoutingSubscriber = this.context.createSocket(SocketType.XPUB);
        this.frontEndSendRoutingSubscriberConnection = frontEndSendRoutingSubscriberConnection;
        System.out.println("RoutingSubscriberZeroMQ connect to frontEndSendRoutingSubscriberConnection: " + this.frontEndSendRoutingSubscriberConnection);
        this.frontEndSendRoutingSubscriber.connect(this.frontEndSendRoutingSubscriberConnection);

        //Receive Proxy
        this.frontEndReceiveRoutingSubscriber = this.context.createSocket(SocketType.XSUB);
        this.frontEndReceiveRoutingSubscriberConnection = frontEndReceiveRoutingSubscriberConnection;
        System.out.println("RoutingSubscriberZeroMQ connect to frontEndReceiveRoutingSubscriberConnection: " + this.frontEndReceiveRoutingSubscriberConnection);
        for(String connection : this.frontEndReceiveRoutingSubscriberConnection) {
            this.frontEndReceiveRoutingSubscriber.connect(connection);
        }

        //Send Worker
        this.backEndSendRoutingSubscriber = this.context.createSocket(SocketType.XSUB);
        this.backEndSendRoutingSubscriberConnection = backEndSendRoutingSubscriberConnection;
        System.out.println("RoutingSubscriberZeroMQ binding to backEndSendRoutingSubscriberConnection: " + this.backEndSendRoutingSubscriberConnection);
        this.backEndSendRoutingSubscriber.bind(this.backEndSendRoutingSubscriberConnection);

        //Receive Worker
        this.backEndReceiveRoutingSubscriber = this.context.createSocket(SocketType.XPUB);
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
