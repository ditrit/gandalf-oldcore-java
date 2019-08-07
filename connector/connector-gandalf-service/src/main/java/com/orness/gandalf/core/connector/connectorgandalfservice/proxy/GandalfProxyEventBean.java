
package com.orness.gandalf.core.connector.connectorgandalfservice.proxy;

import com.orness.gandalf.core.module.gandalfmodule.properties.GandalfProperties;
import com.orness.gandalf.core.module.zeromqmodule.event.proxy.PubSubProxyZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class GandalfProxyEventBean implements Runnable {

    private GandalfProperties gandalfProperties;

    @Autowired
    public GandalfProxyEventBean(GandalfProperties gandalfProperties) {
        this.gandalfProperties = gandalfProperties;
    }

    @Override
    public void run() {
        new PubSubProxyZeroMQ(gandalfProperties.getSubscriberProxy(), gandalfProperties.getPublisherProxy());
    }
}

