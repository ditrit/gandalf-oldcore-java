package com.ditrit.gandalf.core.aggregatorcore.service;

import com.ditrit.gandalf.core.aggregatorcore.properties.AggregatorProperties;
import com.ditrit.gandalf.core.zeromqcore.service.listener.RunnableListenerServiceZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "aggregatorListenerService")
@Scope("singleton")
public class AggregatorListenerService extends RunnableListenerServiceZeroMQ {

    private AggregatorProperties aggregatorProperties;

    @Autowired
    public AggregatorListenerService(AggregatorProperties aggregatorProperties) {
        super();
        this.aggregatorProperties = aggregatorProperties;
        this.initRunnable(this.aggregatorProperties.getAggregatorName(), this.aggregatorProperties.getAggregatorListenerServiceConnection());
    }

    @Override
    public void processRequestService(ZMsg request) {
        if(request.toArray()[0].equals("configuration")) {
            this.frontEndListener.sendMore(this.aggregatorProperties.getAggregatorCommandBackEndReceiveConnection());
            this.frontEndListener.sendMore(this.aggregatorProperties.getAggregatorCommandBackEndSendConnection());
            this.frontEndListener.sendMore(this.aggregatorProperties.getAggregatorEventBackEndReceiveConnection());
            this.frontEndListener.send(this.aggregatorProperties.getAggregatorEventBackEndSendConnection());
        }
    }
}
