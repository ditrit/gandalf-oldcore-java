package com.ditrit.gandalf.core.zeromqcore.command.routing;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.List;

public abstract class RoutingWorkerZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket frontEndRoutingWorker;
    private List<String> frontEndRoutingWorkerConnections;
    protected ZMQ.Socket backEndRoutingWorker;
    private String backEndRoutingWorkerConnection; //IPC
    protected String routingWorkerConnector;

    protected void init(String routingWorkerConnector, List<String> frontEndRoutingWorkerConnections, String backEndRoutingWorkerConnection) {
        this.context = new ZContext();
        this.routingWorkerConnector = routingWorkerConnector;

        //Broker
        this.frontEndRoutingWorker = this.context.createSocket(SocketType.DEALER);
        this.frontEndRoutingWorker.setIdentity(this.routingWorkerConnector.getBytes(ZMQ.CHARSET));
        this.frontEndRoutingWorkerConnections = frontEndRoutingWorkerConnections;
        for(String connection : this.frontEndRoutingWorkerConnections) {
            System.out.println("WorkerZeroMQ connect to frontEndRoutingWorkerConnections: " + connection);
            this.frontEndRoutingWorker.connect(connection);
        }

        //Worker
        this.backEndRoutingWorker = this.context.createSocket(SocketType.ROUTER);
        this.backEndRoutingWorker.setIdentity(this.routingWorkerConnector.getBytes(ZMQ.CHARSET));
        this.backEndRoutingWorkerConnection = backEndRoutingWorkerConnection;
        System.out.println("WorkerZeroMQ binding to backEndRoutingWorkerConnection: " + this.backEndRoutingWorkerConnection);
        this.backEndRoutingWorker.bind(this.backEndRoutingWorkerConnection);
    }

    public void close() {
        this.frontEndRoutingWorker.close();
        this.backEndRoutingWorker.close();
        this.context.close();
    }

    protected void reconnectToBroker() {
        if (this.frontEndRoutingWorker != null) {
            this.context.destroySocket(frontEndRoutingWorker);
        }
        this.init(this.routingWorkerConnector, this.frontEndRoutingWorkerConnections, this.backEndRoutingWorkerConnection);
        // Register service with broker
    }
}
