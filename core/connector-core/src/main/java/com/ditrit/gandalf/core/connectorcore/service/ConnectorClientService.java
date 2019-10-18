package com.ditrit.gandalf.core.connectorcore.service;

import com.ditrit.gandalf.core.connectorcore.properties.ConnectorProperties;
import com.ditrit.gandalf.core.zeromqcore.service.client.RunnableClientServiceZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "connectorClientService")
@Scope("singleton")
public class ConnectorClientService extends RunnableClientServiceZeroMQ {

    private ConnectorProperties connectorProperties;

    @Autowired
    public ConnectorClientService(ConnectorProperties connectorProperties) {
        super();
        this.connectorProperties = connectorProperties;
        this.initRunnable(this.connectorProperties.getConnectorName(), this.connectorProperties.getConnectorClientServiceConnection());
    }

    @Override
    public ZMsg sendRequest(Object request) {
        this.serviceClient.send(request.toString());
        return this.getRequestResult();
    }
}
