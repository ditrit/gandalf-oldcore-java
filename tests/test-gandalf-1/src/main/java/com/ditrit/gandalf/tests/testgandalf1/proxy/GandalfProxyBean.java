package com.ditrit.gandalf.tests.testgandalf1.proxy;

import com.ditrit.gandalf.tests.testgandalf1.properties.GandalfProperties;
import com.ditrit.gandalf.tests.testzeromq.event.Proxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class GandalfProxyBean implements Runnable {

    private GandalfProperties gandalfProperties;

    @Autowired
    public GandalfProxyBean(GandalfProperties gandalfProperties) {
        this.gandalfProperties = gandalfProperties;
    }

    @Override
    public void run() {
        new Proxy(gandalfProperties.getRoutingSubscriberFrontEndConnection(), gandalfProperties.getRoutingSubscriberBackEndConnection(), gandalfProperties.getCaptureProxyBackEndConnection2());
    }
}