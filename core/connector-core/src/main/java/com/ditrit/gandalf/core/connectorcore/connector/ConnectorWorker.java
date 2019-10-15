package com.ditrit.gandalf.core.connectorcore.connector;

import com.ditrit.gandalf.core.connectorcore.properties.ConnectorProperties;
import com.google.gson.Gson;
import com.ditrit.gandalf.core.zeromqcore.command.aggregator.RunnableAggregatorWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "connectorWorker")
public class ConnectorWorker extends RunnableAggregatorWorkerZeroMQ {

    private ConnectorProperties connectorProperties;

    @Autowired
    public ConnectorWorker(ConnectorProperties connectorProperties) {
        super();
        this.connectorProperties = connectorProperties;
        this.mapper = new Gson();
        this.initRunnable(this.connectorProperties.getConnectorName(), this.connectorProperties.getConnectorCommandFrontEndSendConnections(), this.connectorProperties.getConnectorCommandFrontEndReceiveConnections(), this.connectorProperties.getConnectorCommandBackEndSendConnection(), this.connectorProperties.getConnectorCommandBackEndReceiveConnection());
    }
}
