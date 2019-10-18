package com.ditrit.gandalf.core.connectorcore.service;

import com.ditrit.gandalf.core.connectorcore.properties.ConnectorProperties;
import com.ditrit.gandalf.core.zeromqcore.service.listener.RunnableListenerServiceZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "connectorListenerService")
@Scope("singleton")
public class ConnectorListenerService extends RunnableListenerServiceZeroMQ {

    private ConnectorProperties connectorProperties;

    @Autowired
    public ConnectorListenerService(ConnectorProperties connectorProperties) {
        super();
        this.connectorProperties = connectorProperties;
        this.initRunnable(this.connectorProperties.getConnectorName(), this.connectorProperties.getConnectorListenerServiceConnection());
    }

    @Override
    public String processRequestService(ZMsg request) {
        return null;
    }
}
