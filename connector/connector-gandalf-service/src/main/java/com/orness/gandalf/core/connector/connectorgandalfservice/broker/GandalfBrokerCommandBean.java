
package com.orness.gandalf.core.connector.connectorgandalfservice.broker;

import com.orness.gandalf.core.module.gandalfmodule.properties.GandalfProperties;
import com.orness.gandalf.core.module.zeromqmodule.command.broker.BrokerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class GandalfBrokerCommandBean implements Runnable {

    private GandalfProperties gandalfProperties;

    @Autowired
    public GandalfBrokerCommandBean(GandalfProperties gandalfProperties) {
        this.gandalfProperties = gandalfProperties;
    }

    @Override
    public void run() {
        new BrokerZeroMQ(gandalfProperties.getClientBroker(), gandalfProperties.getWorkerBroker());
    }
}

