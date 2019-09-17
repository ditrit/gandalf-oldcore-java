package com.ditrit.gandalf.core.connectorcore.routing;

import com.ditrit.gandalf.core.connectorcore.properties.ConnectorProperties;
import com.google.gson.Gson;
import com.ditrit.gandalf.core.zeromqcore.event.routing.RunnableRoutingSubscriberZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "routingSubscriber")
public class ConnectorRoutingSubscriber extends RunnableRoutingSubscriberZeroMQ {

    private ConnectorProperties connectorProperties;

    @Autowired
    public ConnectorRoutingSubscriber(ConnectorProperties connectorProperties) {
        super();
        this.connectorProperties = connectorProperties;
        this.mapper = new Gson();
        this.initRunnable(this.connectorProperties.getConnectorName(), this.connectorProperties.getConnectorEventFrontEndConnection(), this.connectorProperties.getConnectorEventBackEndConnection());
    }
}
