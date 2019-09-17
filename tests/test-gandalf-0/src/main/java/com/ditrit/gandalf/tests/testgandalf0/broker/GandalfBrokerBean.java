package com.ditrit.gandalf.tests.testgandalf0.broker;

import com.ditrit.gandalf.tests.testgandalf0.properties.GandalfProperties;
import com.ditrit.gandalf.tests.testzeromq.command.Broker;
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
        new Broker(gandalfProperties.getClientBackEndConnection1(), gandalfProperties.getRoutingWorkerFrontEndConnection1(), gandalfProperties.getCaptureBrokerBackEndConnection1());
    }
}
