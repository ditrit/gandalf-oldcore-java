package com.ditrit.gandalf.core.connectorcore.routing;

import com.ditrit.gandalf.core.connectorcore.properties.ConnectorProperties;
import com.google.gson.Gson;
import com.ditrit.gandalf.core.zeromqcore.command.routing.RunnableRoutingWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "routingWorker")
public class ConnectorRoutingWorker  extends RunnableRoutingWorkerZeroMQ {

    private ConnectorProperties connectorProperties;

    @Autowired
    public ConnectorRoutingWorker(ConnectorProperties connectorProperties) {
        super();
        this.connectorProperties = connectorProperties;
        this.mapper = new Gson();
        this.initRunnable(this.connectorProperties.getConnectorName(), this.connectorProperties.getConnectorCommandFrontEndSendConnections(), this.connectorProperties.getConnectorCommandFrontEndReceiveConnections(), this.connectorProperties.getConnectorCommandBackEndConnection());
    }
}
