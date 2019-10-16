package com.ditrit.gandalf.core.zeromqcore.event.connector;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.List;

public abstract class ConnectorEventZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket frontEndSendRoutingConnector;
    protected String frontEndSendRoutingConnectorConnection;
    protected ZMQ.Socket frontEndReceiveRoutingConnector;
    protected List<String> frontEndReceiveRoutingConnectorConnections;
    protected ZMQ.Socket backEndSendRoutingConnector;
    protected String backEndSendRoutingConnectorConnection;
    protected ZMQ.Socket backEndReceiveRoutingConnector;
    protected String backEndReceiveRoutingConnectorConnection;
    protected String identity;

    protected void init(String identity, String frontEndSendRoutingConnectorConnection, String backEndSendRoutingConnectorConnection, List<String> frontEndReceiveRoutingConnectorConnections, String backEndReceiveRoutingConnectorConnection) {
        this.context = new ZContext();
        this.identity = identity;

        this.frontEndSendRoutingConnector = this.context.createSocket(SocketType.XPUB);
        this.frontEndSendRoutingConnectorConnection = frontEndSendRoutingConnectorConnection;
        System.out.println("EventRoutingConnectorZeroMQ connect to frontEndSendRoutingConnectorConnection: " + this.frontEndSendRoutingConnectorConnection);
        this.frontEndSendRoutingConnector.connect(this.frontEndSendRoutingConnectorConnection);

        this.frontEndReceiveRoutingConnector = this.context.createSocket(SocketType.XSUB);
        this.frontEndReceiveRoutingConnectorConnections = frontEndReceiveRoutingConnectorConnections;
        System.out.println("EventRoutingConnectorZeroMQ connect to frontEndReceiveRoutingConnectorConnections: " + this.frontEndReceiveRoutingConnectorConnections);
        for(String connection : this.frontEndReceiveRoutingConnectorConnections) {
            this.frontEndReceiveRoutingConnector.connect(connection);
        }

        this.backEndSendRoutingConnector = this.context.createSocket(SocketType.XSUB);
        this.backEndSendRoutingConnectorConnection = backEndSendRoutingConnectorConnection;
        System.out.println("EventRoutingConnectorZeroMQ binding to backEndSendRoutingConnectorConnection: " + this.backEndSendRoutingConnectorConnection);
        this.backEndSendRoutingConnector.bind(this.backEndSendRoutingConnectorConnection);

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
        this.init(this.identity, this.frontEndSendRoutingConnectorConnection, this.backEndSendRoutingConnectorConnection , this.frontEndReceiveRoutingConnectorConnections, this.backEndReceiveRoutingConnectorConnection);
    }
}
