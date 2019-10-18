package com.ditrit.gandalf.core.aggregatorcore.aggregator;

import com.ditrit.gandalf.core.aggregatorcore.properties.AggregatorProperties;
import com.ditrit.gandalf.core.zeromqcore.command.aggregator.RunnableAggregatorCommandZeroMQ;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value = "aggregatorCommand")
@Scope("singleton")
public class AggregatorCommand extends RunnableAggregatorCommandZeroMQ {

    private AggregatorProperties aggregatorProperties;

    @Autowired
    public AggregatorCommand(AggregatorProperties aggregatorProperties) {
        super();
        this.aggregatorProperties = aggregatorProperties;
        this.mapper = new Gson();
        this.initRunnable(this.aggregatorProperties.getAggregatorName(), this.aggregatorProperties.getAggregatorCommandFrontEndSendConnections(), this.aggregatorProperties.getAggregatorCommandFrontEndReceiveConnections(), this.aggregatorProperties.getAggregatorCommandBackEndSendConnection(), this.aggregatorProperties.getAggregatorCommandBackEndReceiveConnection());
    }
}
