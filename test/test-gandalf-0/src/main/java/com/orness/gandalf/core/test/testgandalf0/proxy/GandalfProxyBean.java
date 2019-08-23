package com.orness.gandalf.core.test.testgandalf0.proxy;

import com.orness.gandalf.core.test.testzeromq.event.Proxy;
import com.orness.gandalf.core.test.testzeromq.gandalf.GandalfProperties;
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
        new Proxy(gandalfProperties.getWorkerBackEndConnection(), gandalfProperties.getSubscriberFrontEndConnection(), gandalfProperties.getCaptureBackEndConnection());
    }
}