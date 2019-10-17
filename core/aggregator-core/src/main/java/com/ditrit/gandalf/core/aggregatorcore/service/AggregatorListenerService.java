package com.ditrit.gandalf.core.aggregatorcore.service;

import com.ditrit.gandalf.core.aggregatorcore.properties.AggregatorProperties;
import com.ditrit.gandalf.core.zeromqcore.service.listener.RunnableListenerServiceZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "aggregatorListenerService")
public class AggregatorListenerService extends RunnableListenerServiceZeroMQ {

    private AggregatorProperties aggregatorProperties;

    @Autowired
    public AggregatorListenerService(AggregatorProperties aggregatorProperties) {
        super();
        this.aggregatorProperties = aggregatorProperties;
        this.initRunnable(this.aggregatorProperties.getAggregatorName(), this.aggregatorProperties.getAggregatorListenerServiceConnection());
    }

    @Override
    public String processRequestService(ZMsg request) {
        return null;
    }
}
