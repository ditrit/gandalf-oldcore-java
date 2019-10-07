package com.ditrit.gandalf.core.zeromqcore.command.routing;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.List;

public abstract class RoutingWorkerZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket frontEndSendRoutingWorker;
    private List<String> frontEndSendRoutingWorkerConnections;
    protected ZMQ.Socket frontEndReceiveRoutingWorker;
    private List<String> frontEndReceiveRoutingWorkerConnections;
    protected ZMQ.Socket backEndRoutingWorker;
    private String backEndRoutingWorkerConnection; //IPC
    protected String routingWorkerConnector;

    protected void init(String routingWorkerConnector, List<String> frontEndSendRoutingWorkerConnections, List<String> frontEndReceiveRoutingWorkerConnections, String backEndRoutingWorkerConnection) {
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

        //Worker
        this.backEndRoutingWorker = this.context.createSocket(SocketType.ROUTER);
        this.backEndRoutingWorker.setIdentity(this.routingWorkerConnector.getBytes(ZMQ.CHARSET));
        this.backEndRoutingWorkerConnection = backEndRoutingWorkerConnection;
        System.out.println("RoutingWorkerZeroMQ binding to backEndReceiveRoutingWorkerConnection: " + this.backEndRoutingWorkerConnection);
        this.backEndRoutingWorker.bind(this.backEndRoutingWorkerConnection);
    }

    public void close() {
        this.frontEndReceiveRoutingWorker.close();
        this.backEndRoutingWorker.close();
        this.context.close();
    }

    protected void reconnectToBroker() {
        if (this.frontEndReceiveRoutingWorker != null) {
            this.context.destroySocket(frontEndReceiveRoutingWorker);
        }
        this.init(this.routingWorkerConnector, this.frontEndSendRoutingWorkerConnections, this.frontEndReceiveRoutingWorkerConnections, this.backEndRoutingWorkerConnection);
        // Register service with broker
    }
}
