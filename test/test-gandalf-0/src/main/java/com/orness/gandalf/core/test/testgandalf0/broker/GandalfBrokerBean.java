package com.orness.gandalf.core.test.testgandalf0.broker;

import com.orness.gandalf.core.test.testzeromq.command.Broker;
import com.orness.gandalf.core.test.testzeromq.gandalf.GandalfProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class GandalfBrokerBean implements Runnable {

    private GandalfProperties gandalfProperties;

    @Autowired
    public GandalfBrokerBean(GandalfProperties gandalfProperties) {
        this.gandalfProperties = gandalfProperties;
    }

    @Override
    public void run() {
        new Broker(gandalfProperties.getClientBackEndConnection1(), gandalfProperties.getWorkerFrontEndConnection1(), gandalfProperties.getCaptureBackEndConnection1());
    }
}