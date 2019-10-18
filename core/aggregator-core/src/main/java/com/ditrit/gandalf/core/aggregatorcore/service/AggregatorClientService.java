package com.ditrit.gandalf.core.aggregatorcore.service;

import com.ditrit.gandalf.core.aggregatorcore.properties.AggregatorProperties;
import com.ditrit.gandalf.core.zeromqcore.service.client.RunnableClientServiceZeroMQ;
import com.ditrit.gandalf.core.zeromqcore.service.listener.RunnableListenerServiceZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "aggregatorClientService")
@Scope("singleton")
public class AggregatorClientService extends RunnableClientServiceZeroMQ {

    private AggregatorProperties aggregatorProperties;

    @Autowired
    public AggregatorClientService(AggregatorProperties aggregatorProperties) {
        super();
        this.aggregatorProperties = aggregatorProperties;
        this.initRunnable(this.aggregatorProperties.getAggregatorName(), this.aggregatorProperties.getAggregatorClientServiceConnection());
    }

    @Override
    public ZMsg sendRequest(Object request) {
        this.serviceClient.send(request.toString());
        return this.getRequestResult();
    }
}
