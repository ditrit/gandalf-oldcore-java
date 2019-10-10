package com.ditrit.gandalf.core.zeromqcore.command.aggregator;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.List;

public abstract class AggregatorWorkerZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket frontEndSendRoutingWorker;
    private List<String> frontEndSendRoutingWorkerConnections;
    protected ZMQ.Socket frontEndReceiveRoutingWorker;
    private List<String> frontEndReceiveRoutingWorkerConnections;
    protected ZMQ.Socket backEndSendRoutingWorker;
    private String backEndSendRoutingWorkerConnection;
    protected ZMQ.Socket backEndReceiveRoutingWorker;
    private String backEndReceiveRoutingWorkerConnection;
    protected String routingWorkerConnector;

    protected void init(String routingWorkerConnector, List<String> frontEndSendRoutingWorkerConnections, List<String> frontEndReceiveRoutingWorkerConnections, String backEndSendRoutingWorkerConnection, String backEndReceiveRoutingWorkerConnection) {
        this.context = new ZContext();
        this.routingWorkerConnector = routingWorkerConnector;

        //Send Broker
        this.frontEndSendRoutingWorker = this.context.createSocket(SocketType.DEALER);
        this.frontEndSendRoutingWorker.setIdentity(this.routingWorkerConnector.getBytes(ZMQ.CHARSET));
        this.frontEndSendRoutingWorkerConnections = frontEndSendRoutingWorkerConnections;
        for(String connection : this.frontEndSendRoutingWorkerConnections) {
            System.out.println("RoutingWorkerZeroMQ connect to frontEndSendRoutingWorkerConnections: " + connection);
            this.frontEndSendRoutingWorker.connect(connection);
        }

        //Receive Broker
        this.frontEndReceiveRoutingWorker = this.context.createSocket(SocketType.DEALER);
        this.frontEndReceiveRoutingWorker.setIdentity(this.routingWorkerConnector.getBytes(ZMQ.CHARSET));
        this.frontEndReceiveRoutingWorkerConnections = frontEndReceiveRoutingWorkerConnections;
        for(String connection : this.frontEndReceiveRoutingWorkerConnections) {
            System.out.println("RoutingWorkerZeroMQ connect to frontEndReceiveRoutingWorkerConnections: " + connection);
            this.frontEndReceiveRoutingWorker.connect(connection);
        }

        //Send Worker
        this.backEndSendRoutingWorker = this.context.createSocket(SocketType.ROUTER);
        this.backEndSendRoutingWorker.setIdentity(this.routingWorkerConnector.getBytes(ZMQ.CHARSET));
        this.backEndSendRoutingWorkerConnection = backEndSendRoutingWorkerConnection;
        System.out.println("RoutingWorkerZeroMQ binding to backEndSendRoutingWorkerConnection: " + this.backEndSendRoutingWorkerConnection);
        this.backEndSendRoutingWorker.bind(this.backEndSendRoutingWorkerConnection);

        //Receive Worker
        this.backEndReceiveRoutingWorker = this.context.createSocket(SocketType.ROUTER);
        this.backEndReceiveRoutingWorker.setIdentity(this.routingWorkerConnector.getBytes(ZMQ.CHARSET));
        this.backEndReceiveRoutingWorkerConnection = backEndReceiveRoutingWorkerConnection;
        System.out.println("RoutingWorkerZeroMQ binding to backEndReceiveRoutingWorkerConnection: " + this.backEndReceiveRoutingWorkerConnection);
        this.backEndReceiveRoutingWorker.bind(this.backEndReceiveRoutingWorkerConnection);
    }

    public void close() {
        this.frontEndSendRoutingWorker.close();
        this.frontEndReceiveRoutingWorker.close();
        this.backEndSendRoutingWorker.close();
        this.backEndReceiveRoutingWorker.close();
        this.context.close();
    }

    protected void reconnectToBroker() {
        if (this.frontEndReceiveRoutingWorker != null) {
            this.context.destroySocket(frontEndReceiveRoutingWorker);
        }
        this.init(this.routingWorkerConnector, this.frontEndSendRoutingWorkerConnections, this.frontEndReceiveRoutingWorkerConnections, this.backEndSendRoutingWorkerConnection, this.backEndReceiveRoutingWorkerConnection);
        // Register service with broker
    }
}
