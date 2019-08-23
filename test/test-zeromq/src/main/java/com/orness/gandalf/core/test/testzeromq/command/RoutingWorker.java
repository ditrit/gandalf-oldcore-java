package com.orness.gandalf.core.test.testzeromq.command;

import org.zeromq.*;

import static com.orness.gandalf.core.test.testzeromq.Constant.*;

public abstract class RoutingWorker {

    protected ZContext context;
    protected static ZMQ.Socket frontEndRoutingWorker;
    protected String[] frontEndRoutingWorkerConnections;
    protected static ZMQ.Socket backEndRoutingWorker;
    protected String backEndRoutingWorkerConnection; //IPC
    protected String routingWorkerConnector;

    public RoutingWorker() {
    }

    protected void init(String routingWorkerConnector, String[] frontEndRoutingWorkerConnections, String backEndRoutingWorkerConnection) {
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

    protected void close() {
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
        this.sendReadyCommand();
    }

    protected void sendReadyCommand() {
        this.frontEndRoutingWorker.sendMore(WORKER_COMMAND_READY);
        this.frontEndRoutingWorker.send(this.routingWorkerConnector);
    }
}