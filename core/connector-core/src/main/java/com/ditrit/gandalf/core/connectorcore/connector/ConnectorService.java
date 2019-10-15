package com.ditrit.gandalf.core.connectorcore.connector;

import com.ditrit.gandalf.core.connectorcore.properties.ConnectorProperties;
import com.ditrit.gandalf.core.zeromqcore.service.listener.RunnableListenerServiceZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "connectorService")
public class ConnectorService extends RunnableListenerServiceZeroMQ {

    private ConnectorProperties connectorProperties;

    @Autowired
    public ConnectorService(ConnectorProperties connectorProperties) {
        super();
        this.connectorProperties = connectorProperties;
        this.initRunnable(this.connectorProperties.getConnectorName(), this.connectorProperties.getConnectorServiceConnection());
    }
}
