package com.ditrit.gandalf.core.aggregatorcore.aggregator;

import com.ditrit.gandalf.core.aggregatorcore.properties.AggregatorProperties;
import com.ditrit.gandalf.core.zeromqcore.service.listener.RunnableListenerServiceZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "aggregatorService")
public class AggregatorService extends RunnableListenerServiceZeroMQ {

    private AggregatorProperties aggregatorProperties;

    @Autowired
    public AggregatorService(AggregatorProperties aggregatorProperties) {
        super();
        this.aggregatorProperties = aggregatorProperties;
        this.initRunnable(this.aggregatorProperties.getAggregatorName(), this.aggregatorProperties.getAggregatorServiceConnection());
    }
}
