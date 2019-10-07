package com.orness.gandalf.core.module.connectorcore.routing;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import com.orness.gandalf.core.module.zeromqcore.command.routing.RunnableRoutingWorkerZeroMQ;
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
