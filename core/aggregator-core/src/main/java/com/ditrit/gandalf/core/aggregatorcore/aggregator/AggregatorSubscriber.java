package com.ditrit.gandalf.core.aggregatorcore.aggregator;

import com.ditrit.gandalf.core.aggregatorcore.properties.AggregatorProperties;
import com.ditrit.gandalf.core.zeromqcore.event.aggregator.RunnableAggregatorSubscriberZeroMQ;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "aggregatorSubscriber")
public class AggregatorSubscriber extends RunnableAggregatorSubscriberZeroMQ {

    private AggregatorProperties aggregatorProperties;

    @Autowired
    public AggregatorSubscriber(AggregatorProperties aggregatorProperties) {
        super();
        this.aggregatorProperties = aggregatorProperties;
        this.mapper = new Gson();
        //String routingSubscriberConnector, String frontEndSendRoutingSubscriberConnection, String backEndSendRoutingSubscriberConnection, String frontEndReceiveRoutingSubscriberConnection, String backEndReceiveRoutingSubscriberConnection
        this.initRunnable(this.aggregatorProperties.getAggregatorName(), this.aggregatorProperties.getConnectorEventFrontEndSendConnection(), this.aggregatorProperties.getConnectorEventBackEndSendConnection(), this.aggregatorProperties.getConnectorEventFrontEndReceiveConnection(), this.aggregatorProperties.getConnectorEventBackEndReceiveConnection());
    }
}
