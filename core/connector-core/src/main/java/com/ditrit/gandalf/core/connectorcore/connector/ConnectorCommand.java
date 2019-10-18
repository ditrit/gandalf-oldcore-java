package com.ditrit.gandalf.core.connectorcore.connector;

import com.ditrit.gandalf.core.connectorcore.properties.ConnectorProperties;
import com.ditrit.gandalf.core.zeromqcore.command.connector.RunnableConnectorCommandZeroMQ;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value = "connectorCommand")
@Scope("singleton")
public class ConnectorCommand extends RunnableConnectorCommandZeroMQ {

    private ConnectorProperties connectorProperties;

    @Autowired
    public ConnectorCommand(ConnectorProperties connectorProperties) {
        super();
        this.connectorProperties = connectorProperties;
        this.mapper = new Gson();
        this.initRunnable(this.connectorProperties.getConnectorName(), this.connectorProperties.getConnectorCommandFrontEndSendConnection(), this.connectorProperties.getConnectorCommandFrontEndReceiveConnection(), this.connectorProperties.getConnectorCommandBackEndSendConnection(), this.connectorProperties.getConnectorCommandBackEndReceiveConnection());
    }
}
