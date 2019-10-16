package com.ditrit.gandalf.core.zeromqcore.event.aggregator;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.List;

public abstract class AggregatorEventZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket frontEndSendRoutingAggregator;
    protected String frontEndSendRoutingAggregatorConnection;
    protected ZMQ.Socket frontEndReceiveRoutingAggregator;
    protected List<String> frontEndReceiveRoutingAggregatorConnection;
    protected ZMQ.Socket backEndSendRoutingAggregator;
    protected String backEndSendRoutingAggregatorConnection;
    protected ZMQ.Socket backEndReceiveRoutingAggregator;
    protected String backEndReceiveRoutingAggregatorConnection;
    protected String identity;

    protected void init(String identity, String frontEndSendRoutingAggregatorConnection, String backEndSendRoutingAggregatorConnection, List<String> frontEndReceiveRoutingAggregatorConnection, String backEndReceiveRoutingAggregatorConnection) {
        this.context = new ZContext();
        this.identity = identity;

        //Send Proxy
        this.frontEndSendRoutingAggregator = this.context.createSocket(SocketType.XPUB);
        this.frontEndSendRoutingAggregatorConnection = frontEndSendRoutingAggregatorConnection;
        System.out.println("EventRoutingAggregatorZeroMQ connect to frontEndSendRoutingAggregatorConnection: " + this.frontEndSendRoutingAggregatorConnection);
        this.frontEndSendRoutingAggregator.connect(this.frontEndSendRoutingAggregatorConnection);

        //Receive Proxy
        this.frontEndReceiveRoutingAggregator = this.context.createSocket(SocketType.XSUB);
        this.frontEndReceiveRoutingAggregatorConnection = frontEndReceiveRoutingAggregatorConnection;
        System.out.println("EventRoutingAggregatorZeroMQ connect to frontEndReceiveRoutingAggregatorConnection: " + this.frontEndReceiveRoutingAggregatorConnection);
        for(String connection : this.frontEndReceiveRoutingAggregatorConnection) {
            this.frontEndReceiveRoutingAggregator.connect(connection);
        }

        //Send Worker
        this.backEndSendRoutingAggregator = this.context.createSocket(SocketType.XSUB);
        this.backEndSendRoutingAggregatorConnection = backEndSendRoutingAggregatorConnection;
        System.out.println("EventRoutingAggregatorZeroMQ binding to backEndSendRoutingAggregatorConnection: " + this.backEndSendRoutingAggregatorConnection);
        this.backEndSendRoutingAggregator.bind(this.backEndSendRoutingAggregatorConnection);

        //Receive Worker
        this.backEndReceiveRoutingAggregator = this.context.createSocket(SocketType.XPUB);
        this.backEndReceiveRoutingAggregatorConnection = backEndReceiveRoutingAggregatorConnection;
        System.out.println("EventRoutingAggregatorZeroMQ binding to backEndReceiveRoutingAggregatorConnection: " + this.backEndReceiveRoutingAggregatorConnection);
        this.backEndReceiveRoutingAggregator.bind(this.backEndReceiveRoutingAggregatorConnection);
    }

    public void close() {
        this.frontEndSendRoutingAggregator.close();
        this.frontEndReceiveRoutingAggregator.close();
        this.backEndSendRoutingAggregator.close();
        this.backEndReceiveRoutingAggregator.close();
        this.context.close();
    }

    protected void reconnectToProxy() {
        if (this.frontEndSendRoutingAggregator != null) {
            this.context.destroySocket(frontEndSendRoutingAggregator);
        }
        if (this.frontEndReceiveRoutingAggregator != null) {
            this.context.destroySocket(frontEndReceiveRoutingAggregator);
        }
        this.init(this.identity, this.frontEndSendRoutingAggregatorConnection, this.backEndSendRoutingAggregatorConnection , this.frontEndReceiveRoutingAggregatorConnection, this.backEndReceiveRoutingAggregatorConnection);
    }
}
