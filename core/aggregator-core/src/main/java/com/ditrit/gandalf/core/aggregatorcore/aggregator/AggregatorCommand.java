package com.ditrit.gandalf.core.aggregatorcore.aggregator;

import com.ditrit.gandalf.core.aggregatorcore.properties.AggregatorProperties;
import com.ditrit.gandalf.core.zeromqcore.command.aggregator.RunnableAggregatorCommandZeroMQ;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        this.getConfiguration();
    }

    private void getConfiguration() {
        ZMsg req_configuration = new ZMsg();
        req_configuration.add("CONFIGURATION");
        req_configuration.add(this.aggregatorProperties.getAggregatorName());
        req_configuration.add(this.aggregatorProperties.getInstanceName());
        req_configuration.send(this.frontEndSendRoutingAggregator);
        req_configuration.destroy();

        ZMsg rep_configuration = null;

        while(rep_configuration == null) {
            rep_configuration = ZMsg.recvMsg(this.frontEndSendRoutingAggregator, ZMQ.NOBLOCK);
            boolean more = this.frontEndSendRoutingAggregator.hasReceiveMore();

            if(more) {
                break;
            }
        }

        Object[] configurationArray = rep_configuration.toArray();
        rep_configuration.destroy();

        List<String> configurationFrontEndReceiveConnection = new ArrayList<>();
        Arrays.stream(Arrays.copyOfRange(configurationArray, 5, 7))
                .collect(Collectors.toList());

        // PUB SEND
        this.aggregatorProperties.setAggregatorEventFrontEndReceiveConnection(configurationFrontEndReceiveConnection);
        // PUB RECEIVE
        this.aggregatorProperties.setAggregatorEventFrontEndSendConnection(configurationArray[8].toString());
        //TODO
        //REQ AGGREGATOR
        // NOM
        // AGGREGATOR
        // UUID
        // IP
        // MAC
        // TYPE C
        // TYPE P
    }
}
