package com.ditrit.gandalf.core.aggregatorcore.aggregator;

import com.ditrit.gandalf.core.aggregatorcore.properties.AggregatorProperties;
import com.ditrit.gandalf.core.zeromqcore.event.aggregator.RunnableAggregatorEventZeroMQ;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value = "aggregatorEvent")
@Scope("singleton")
public class AggregatorEvent extends RunnableAggregatorEventZeroMQ {

    private AggregatorProperties aggregatorProperties;

    @Autowired
    public AggregatorEvent(AggregatorProperties aggregatorProperties) {
        super();
        this.aggregatorProperties = aggregatorProperties;
        this.mapper = new Gson();
        this.initRunnable(this.aggregatorProperties.getAggregatorName(), this.aggregatorProperties.getAggregatorEventFrontEndSendConnection(), this.aggregatorProperties.getAggregatorEventBackEndSendConnection(), this.aggregatorProperties.getAggregatorEventFrontEndReceiveConnection(), this.aggregatorProperties.getAggregatorEventBackEndReceiveConnection());
    }
}
