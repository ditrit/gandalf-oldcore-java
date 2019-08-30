
package com.orness.gandalf.core.connector.connectorgandalf.proxy;

import com.orness.gandalf.core.module.gandalfmodule.properties.ConnectorGandalfProperties;
import com.orness.gandalf.core.module.zeromqmodule.event.proxy.PubSubProxyZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class GandalfProxyEventBean implements Runnable {

    private ConnectorGandalfProperties connectorGandalfProperties;

    @Autowired
    public GandalfProxyEventBean(ConnectorGandalfProperties connectorGandalfProperties) {
        this.connectorGandalfProperties = connectorGandalfProperties;
    }

    @Override
    public void run() {
        new PubSubProxyZeroMQ(connectorGandalfProperties.getSubscriberProxy(), connectorGandalfProperties.getPublisherProxy());
    }
}

