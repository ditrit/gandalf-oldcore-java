package com.ditrit.gandalf.gandalfjava.core.zeromqcore.command.aggregator;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.List;

public abstract class AggregatorCommandZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket backEndSendRoutingAggregator;
    private List<String> backEndSendRoutingAggregatorConnections;
    protected ZMQ.Socket frontEndReceiveRoutingAggregator;
    private List<String> frontEndReceiveRoutingAggregatorConnections;
    protected ZMQ.Socket frontEndSendRoutingAggregator;
    private String frontEndSendRoutingAggregatorConnection;
    protected ZMQ.Socket backEndReceiveRoutingAggregator;
    private String backEndReceiveRoutingAggregatorConnection;
    protected String identity;

    protected void init(String identity, List<String> backEndSendRoutingAggregatorConnections, List<String> frontEndReceiveRoutingAggregatorConnections, String backEndSendRoutingAggregatorConnection, String backEndReceiveRoutingAggregatorConnection) {
        this.context = new ZContext();
        this.identity = identity;

        this.backEndSendRoutingAggregator = this.context.createSocket(SocketType.DEALER);
        this.backEndSendRoutingAggregator.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.backEndSendRoutingAggregatorConnections = backEndSendRoutingAggregatorConnections;
        System.out.println("CommandRoutingAggregatorZeroMQ connect to backEndSendRoutingAggregatorConnections: " + this.backEndSendRoutingAggregatorConnections);
        for(String connection : this.backEndSendRoutingAggregatorConnections) {
            this.backEndSendRoutingAggregator.connect(connection);
        }

        this.frontEndReceiveRoutingAggregator = this.context.createSocket(SocketType.DEALER);
        this.frontEndReceiveRoutingAggregator.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.frontEndReceiveRoutingAggregatorConnections = frontEndReceiveRoutingAggregatorConnections;
        System.out.println("CommandRoutingAggregatorZeroMQ connect to frontEndReceiveRoutingAggregatorConnections: " + this.frontEndReceiveRoutingAggregatorConnections);
        for(String connection : this.frontEndReceiveRoutingAggregatorConnections) {
            this.frontEndReceiveRoutingAggregator.connect(connection);
        }

        this.frontEndSendRoutingAggregator = this.context.createSocket(SocketType.ROUTER);
        this.frontEndSendRoutingAggregator.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.frontEndSendRoutingAggregatorConnection = frontEndSendRoutingAggregatorConnection;
        System.out.println("CommandRoutingAggregatorZeroMQ binding to frontEndSendRoutingAggregatorConnection: " + this.frontEndSendRoutingAggregatorConnection);
        this.frontEndSendRoutingAggregator.bind(this.frontEndSendRoutingAggregatorConnection);

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
        this.init(this.identity, this.backEndSendRoutingAggregatorConnections, this.frontEndReceiveRoutingAggregatorConnections, this.frontEndSendRoutingAggregatorConnection, this.backEndReceiveRoutingAggregatorConnection);
    }
}
