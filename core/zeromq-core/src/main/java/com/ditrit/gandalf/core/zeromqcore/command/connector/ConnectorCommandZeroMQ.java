package com.ditrit.gandalf.core.zeromqcore.command.connector;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.List;

public abstract class ConnectorCommandZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket frontEndSendRoutingConnector;
    private String frontEndSendRoutingConnectorConnection;
    protected ZMQ.Socket frontEndReceiveRoutingConnector;
    private String frontEndReceiveRoutingConnectorConnection;
    protected ZMQ.Socket backEndSendRoutingConnector;
    private String backEndSendRoutingConnectorConnection;
    protected ZMQ.Socket backEndReceiveRoutingConnector;
    private String backEndReceiveRoutingConnectorConnection;
    protected String identity;

    protected void init(String identity, String frontEndSendRoutingConnectorConnection, String frontEndReceiveRoutingConnectorConnection, String backEndSendRoutingConnectorConnection, String backEndReceiveRoutingConnectorConnection) {
        this.context = new ZContext();
        this.identity = identity;

        this.frontEndSendRoutingConnector = this.context.createSocket(SocketType.DEALER);
        this.frontEndSendRoutingConnector.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.frontEndSendRoutingConnectorConnection = frontEndSendRoutingConnectorConnection;
        System.out.println("CommandRoutingConnectorZeroMQ connect to frontEndSendRoutingConnectorConnection: " + this.frontEndSendRoutingConnectorConnection);
        this.frontEndSendRoutingConnector.connect(this.frontEndSendRoutingConnectorConnection);


        this.frontEndReceiveRoutingConnector = this.context.createSocket(SocketType.DEALER);
        this.frontEndReceiveRoutingConnector.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.frontEndReceiveRoutingConnectorConnection = frontEndReceiveRoutingConnectorConnection;
        System.out.println("CommandRoutingConnectorZeroMQ connect to frontEndReceiveRoutingConnectorConnection: " + this.frontEndReceiveRoutingConnectorConnection);
        this.frontEndReceiveRoutingConnector.connect(this.frontEndReceiveRoutingConnectorConnection);


        this.backEndSendRoutingConnector = this.context.createSocket(SocketType.ROUTER);
        this.backEndSendRoutingConnector.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.backEndSendRoutingConnectorConnection = backEndSendRoutingConnectorConnection;
        System.out.println("CommandRoutingConnectorZeroMQ binding to backEndSendRoutingConnectorConnection: " + this.backEndSendRoutingConnectorConnection);
        this.backEndSendRoutingConnector.bind(this.backEndSendRoutingConnectorConnection);

        this.backEndReceiveRoutingConnector = this.context.createSocket(SocketType.ROUTER);
        this.backEndReceiveRoutingConnector.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.backEndReceiveRoutingConnectorConnection = backEndReceiveRoutingConnectorConnection;
        System.out.println("CommandRoutingConnectorZeroMQ binding to backEndReceiveRoutingConnectorConnection: " + this.backEndReceiveRoutingConnectorConnection);
        this.backEndReceiveRoutingConnector.bind(this.backEndReceiveRoutingConnectorConnection);
    }

    public void close() {
        this.frontEndSendRoutingConnector.close();
        this.frontEndReceiveRoutingConnector.close();
        this.backEndSendRoutingConnector.close();
        this.backEndReceiveRoutingConnector.close();
        this.context.close();
    }

    protected void reconnectToBroker() {
        if (this.frontEndReceiveRoutingConnector != null) {
            this.context.destroySocket(frontEndReceiveRoutingConnector);
        }
        if (this.frontEndSendRoutingConnector != null) {
            this.context.destroySocket(frontEndSendRoutingConnector);
        }
        this.init(this.identity, this.frontEndSendRoutingConnectorConnection, this.frontEndReceiveRoutingConnectorConnection, this.backEndSendRoutingConnectorConnection, this.backEndReceiveRoutingConnectorConnection);
    }
}
