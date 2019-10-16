package com.ditrit.gandalf.core.zeromqcore.command.aggregator;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.List;

public abstract class AggregatorCommandZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket frontEndSendRoutingAggregator;
    private List<String> frontEndSendRoutingAggregatorConnections;
    protected ZMQ.Socket frontEndReceiveRoutingAggregator;
    private List<String> frontEndReceiveRoutingAggregatorConnections;
    protected ZMQ.Socket backEndSendRoutingAggregator;
    private String backEndSendRoutingAggregatorConnection;
    protected ZMQ.Socket backEndReceiveRoutingAggregator;
    private String backEndReceiveRoutingAggregatorConnection;
    protected String identity;

    protected void init(String identity, List<String> frontEndSendRoutingAggregatorConnections, List<String> frontEndReceiveRoutingAggregatorConnections, String backEndSendRoutingAggregatorConnection, String backEndReceiveRoutingAggregatorConnection) {
        this.context = new ZContext();
        this.identity = identity;

        //Send Broker
        this.frontEndSendRoutingAggregator = this.context.createSocket(SocketType.DEALER);
        this.frontEndSendRoutingAggregator.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.frontEndSendRoutingAggregatorConnections = frontEndSendRoutingAggregatorConnections;
        System.out.println("CommandRoutingAggregatorZeroMQ connect to frontEndSendRoutingAggregatorConnections: " + this.frontEndSendRoutingAggregatorConnections);
        for(String connection : this.frontEndSendRoutingAggregatorConnections) {
            this.frontEndSendRoutingAggregator.connect(connection);
        }

        //Receive Broker
        this.frontEndReceiveRoutingAggregator = this.context.createSocket(SocketType.DEALER);
        this.frontEndReceiveRoutingAggregator.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.frontEndReceiveRoutingAggregatorConnections = frontEndReceiveRoutingAggregatorConnections;
        System.out.println("CommandRoutingAggregatorZeroMQ connect to frontEndReceiveRoutingAggregatorConnections: " + this.frontEndReceiveRoutingAggregatorConnections);
        for(String connection : this.frontEndReceiveRoutingAggregatorConnections) {
            this.frontEndReceiveRoutingAggregator.connect(connection);
        }

        //Send Worker
        this.backEndSendRoutingAggregator = this.context.createSocket(SocketType.ROUTER);
        this.backEndSendRoutingAggregator.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.backEndSendRoutingAggregatorConnection = backEndSendRoutingAggregatorConnection;
        System.out.println("CommandRoutingAggregatorZeroMQ binding to backEndSendRoutingAggregatorConnection: " + this.backEndSendRoutingAggregatorConnection);
        this.backEndSendRoutingAggregator.bind(this.backEndSendRoutingAggregatorConnection);

        //Receive Worker
        this.backEndReceiveRoutingAggregator = this.context.createSocket(SocketType.ROUTER);
        this.backEndReceiveRoutingAggregator.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.backEndReceiveRoutingAggregatorConnection = backEndReceiveRoutingAggregatorConnection;
        System.out.println("CommandRoutingAggregatorZeroMQ binding to backEndReceiveRoutingAggregatorConnection: " + this.backEndReceiveRoutingAggregatorConnection);
        this.backEndReceiveRoutingAggregator.bind(this.backEndReceiveRoutingAggregatorConnection);
    }

    public void close() {
        this.frontEndSendRoutingAggregator.close();
        this.frontEndReceiveRoutingAggregator.close();
        this.backEndSendRoutingAggregator.close();
        this.backEndReceiveRoutingAggregator.close();
        this.context.close();
    }

    protected void reconnectToBroker() {
        if (this.frontEndReceiveRoutingAggregator != null) {
            this.context.destroySocket(frontEndReceiveRoutingAggregator);
        }
        if (this.frontEndSendRoutingAggregator != null) {
            this.context.destroySocket(frontEndSendRoutingAggregator);
        }
        this.init(this.identity, this.frontEndSendRoutingAggregatorConnections, this.frontEndSendRoutingAggregatorConnections, this.backEndSendRoutingAggregatorConnection, this.backEndReceiveRoutingAggregatorConnection);
        // Register service with broker
    }
}
