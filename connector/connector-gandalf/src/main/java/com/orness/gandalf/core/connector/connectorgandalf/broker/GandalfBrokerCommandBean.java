
package com.orness.gandalf.core.connector.connectorgandalf.broker;

import com.orness.gandalf.core.module.gandalfmodule.properties.ConnectorGandalfProperties;
import com.orness.gandalf.core.module.zeromqmodule.command.broker.BrokerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class GandalfBrokerCommandBean implements Runnable {

    private ConnectorGandalfProperties connectorGandalfProperties;

    @Autowired
    public GandalfBrokerCommandBean(ConnectorGandalfProperties connectorGandalfProperties) {
        this.connectorGandalfProperties = connectorGandalfProperties;
    }

    @Override
    public void run() {
        new BrokerZeroMQ(connectorGandalfProperties.getClientBroker(), connectorGandalfProperties.getWorkerBroker());
    }
}

