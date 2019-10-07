package com.orness.gandalf.core.module.connectorcore.routing;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import com.orness.gandalf.core.module.zeromqcore.event.routing.RunnableRoutingSubscriberZeroMQ;
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
        //String routingSubscriberConnector, String frontEndSendRoutingSubscriberConnection, String backEndSendRoutingSubscriberConnection, String frontEndReceiveRoutingSubscriberConnection, String backEndReceiveRoutingSubscriberConnection
        this.initRunnable(this.connectorProperties.getConnectorName(), this.connectorProperties.getConnectorEventFrontEndSendConnection(), this.connectorProperties.getConnectorEventBackEndSendConnection(), this.connectorProperties.getConnectorEventFrontEndReceiveConnection(), this.connectorProperties.getConnectorEventBackEndReceiveConnection());
    }
}
