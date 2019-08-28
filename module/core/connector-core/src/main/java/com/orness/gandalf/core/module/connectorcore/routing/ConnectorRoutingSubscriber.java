package com.orness.gandalf.core.module.connectorcore.routing;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import com.orness.gandalf.core.module.connectorcore.properties.ConnectorRoutingProperties;
import com.orness.gandalf.core.module.zeromqcore.event.routing.RunnableRoutingSubscriberZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectorRoutingSubscriber extends RunnableRoutingSubscriberZeroMQ {

    private ConnectorProperties connectorProperties;
    private ConnectorRoutingProperties connectorRoutingProperties;

    @Autowired
    public ConnectorRoutingSubscriber(ConnectorProperties connectorProperties, ConnectorRoutingProperties connectorRoutingProperties) {
        super();
        this.connectorProperties = connectorProperties;
        this.connectorRoutingProperties = connectorRoutingProperties;
        this.mapper = new Gson();
        this.initRunnable(this.connectorProperties.getName(), this.connectorRoutingProperties.getRoutingSubscriberFrontEndConnection(), this.connectorRoutingProperties.getRoutingSubscriberBackEndConnection());
    }
}
