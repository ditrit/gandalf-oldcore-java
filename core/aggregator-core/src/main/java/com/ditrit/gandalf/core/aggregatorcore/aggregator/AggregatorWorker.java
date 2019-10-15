package com.ditrit.gandalf.core.aggregatorcore.aggregator;

import com.ditrit.gandalf.core.aggregatorcore.properties.AggregatorProperties;
import com.ditrit.gandalf.core.zeromqcore.command.aggregator.RunnableAggregatorWorkerZeroMQ;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "aggregatorWorker")
public class AggregatorWorker extends RunnableAggregatorWorkerZeroMQ {

    private AggregatorProperties aggregatorProperties;

    @Autowired
    public AggregatorWorker(AggregatorProperties aggregatorProperties) {
        super();
        this.aggregatorProperties = aggregatorProperties;
        this.mapper = new Gson();
        this.initRunnable(this.aggregatorProperties.getAggregatorName(), this.aggregatorProperties.getConnectorCommandFrontEndSendConnections(), this.aggregatorProperties.getConnectorCommandFrontEndReceiveConnections(), this.aggregatorProperties.getConnectorCommandBackEndSendConnection(), this.aggregatorProperties.getConnectorCommandBackEndReceiveConnection());
    }
}
