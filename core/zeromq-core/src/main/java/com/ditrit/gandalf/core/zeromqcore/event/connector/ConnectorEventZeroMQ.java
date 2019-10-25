package com.ditrit.gandalf.core.zeromqcore.event.connector;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.List;

public abstract class ConnectorEventZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket backEndSendRoutingConnector;
    protected String backEndSendRoutingConnectorConnection;
    protected ZMQ.Socket frontEndReceiveRoutingConnector;
    protected String frontEndReceiveRoutingConnectorConnection;
    protected ZMQ.Socket frontEndSendRoutingConnector;
    protected String frontEndSendRoutingConnectorConnection;
    protected ZMQ.Socket backEndReceiveRoutingConnector;
    protected String backEndReceiveRoutingConnectorConnection;
    protected String identity;

    protected void init(String identity, String backEndSendRoutingConnectorConnection, String frontEndSendRoutingConnectorConnection, String frontEndReceiveRoutingConnectorConnection, String backEndReceiveRoutingConnectorConnection) {
        this.context = new ZContext();
        this.identity = identity;

        this.backEndSendRoutingConnector = this.context.createSocket(SocketType.XPUB);
        this.backEndSendRoutingConnectorConnection = backEndSendRoutingConnectorConnection;
        System.out.println("EventRoutingConnectorZeroMQ connect to backEndSendRoutingConnectorConnection: " + this.backEndSendRoutingConnectorConnection);
        this.backEndSendRoutingConnector.connect(this.backEndSendRoutingConnectorConnection);

        this.frontEndReceiveRoutingConnector = this.context.createSocket(SocketType.XSUB);
        this.frontEndReceiveRoutingConnectorConnection = frontEndReceiveRoutingConnectorConnection;
        System.out.println("EventRoutingConnectorZeroMQ connect to frontEndReceiveRoutingConnectorConnections: " + this.frontEndReceiveRoutingConnectorConnection);
        this.frontEndReceiveRoutingConnector.connect(this.frontEndReceiveRoutingConnectorConnection);

        this.frontEndSendRoutingConnector = this.context.createSocket(SocketType.XSUB);
        this.frontEndSendRoutingConnectorConnection = frontEndSendRoutingConnectorConnection;
        System.out.println("EventRoutingConnectorZeroMQ binding to frontEndSendRoutingConnectorConnection: " + this.frontEndSendRoutingConnectorConnection);
        this.frontEndSendRoutingConnector.bind(this.frontEndSendRoutingConnectorConnection);

        this.backEndReceiveRoutingConnector = this.context.createSocket(SocketType.XPUB);
        this.backEndReceiveRoutingConnectorConnection = backEndReceiveRoutingConnectorConnection;
        System.out.println("EventRoutingConnectorZeroMQ binding to backEndReceiveRoutingConnectorConnection: " + this.backEndReceiveRoutingConnectorConnection);
        this.backEndReceiveRoutingConnector.bind(this.backEndReceiveRoutingConnectorConnection);
    }

    public void close() {
        this.frontEndSendRoutingConnector.close();
        this.frontEndReceiveRoutingConnector.close();
        this.backEndSendRoutingConnector.close();
        this.backEndReceiveRoutingConnector.close();
        this.context.close();
    }

    protected void reconnectToProxy() {
        if (this.frontEndSendRoutingConnector != null) {
            this.context.destroySocket(frontEndSendRoutingConnector);
        }
        if (this.frontEndReceiveRoutingConnector != null) {
            this.context.destroySocket(frontEndReceiveRoutingConnector);
        }
        this.init(this.identity, this.backEndSendRoutingConnectorConnection, this.frontEndSendRoutingConnectorConnection , this.frontEndReceiveRoutingConnectorConnection, this.backEndReceiveRoutingConnectorConnection);
    }
}
