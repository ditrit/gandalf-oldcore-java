package com.ditrit.gandalf.core.connectorcore.connector;

import com.ditrit.gandalf.core.connectorcore.properties.ConnectorProperties;
import com.ditrit.gandalf.core.zeromqcore.event.connector.RunnableConnectorEventZeroMQ;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value = "connectorEvent")
@Scope("singleton")
public class ConnectorEvent extends RunnableConnectorEventZeroMQ {

    private ConnectorProperties connectorProperties;

    @Autowired
    public ConnectorEvent(ConnectorProperties connectorProperties) {
        super();
        this.connectorProperties = connectorProperties;
        this.mapper = new Gson();
        //String routingSubscriberConnector, String frontEndSendRoutingSubscriberConnection, String backEndSendRoutingSubscriberConnection, String frontEndReceiveRoutingSubscriberConnection, String backEndReceiveRoutingSubscriberConnection
        this.initRunnable(this.connectorProperties.getConnectorName(), this.connectorProperties.getConnectorEventFrontEndSendConnection(), this.connectorProperties.getConnectorEventBackEndSendConnection(), this.connectorProperties.getConnectorEventFrontEndReceiveConnection(), this.connectorProperties.getConnectorEventBackEndReceiveConnection());
    }
}
