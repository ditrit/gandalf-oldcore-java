package com.ditrit.gandalf.core.connectorcore.aggregator;

import com.ditrit.gandalf.core.connectorcore.properties.ConnectorProperties;
import com.google.gson.Gson;
import com.ditrit.gandalf.core.zeromqcore.event.routing.RunnableAggregatorSubscriberZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "aggregatorSubscriber")
public class ConnectorAggregatorSubscriber extends RunnableAggregatorSubscriberZeroMQ {

    private ConnectorProperties connectorProperties;

    @Autowired
    public ConnectorAggregatorSubscriber(ConnectorProperties connectorProperties) {
        super();
        this.connectorProperties = connectorProperties;
        this.mapper = new Gson();
        //String routingSubscriberConnector, String frontEndSendRoutingSubscriberConnection, String backEndSendRoutingSubscriberConnection, String frontEndReceiveRoutingSubscriberConnection, String backEndReceiveRoutingSubscriberConnection
        this.initRunnable(this.connectorProperties.getConnectorName(), this.connectorProperties.getConnectorEventFrontEndSendConnection(), this.connectorProperties.getConnectorEventBackEndSendConnection(), this.connectorProperties.getConnectorEventFrontEndReceiveConnection(), this.connectorProperties.getConnectorEventBackEndReceiveConnection());
    }
}
