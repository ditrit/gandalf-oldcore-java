package com.ditrit.gandalf.tests.testworker.core;

import com.ditrit.gandalf.tests.testworker.properties.GandalfProperties;
import com.google.gson.Gson;
import com.ditrit.gandalf.tests.testzeromq.event.RunnableRoutingSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GandalfRoutingSubscriber extends RunnableRoutingSubscriber {

    private GandalfProperties gandalfProperties;

    @Autowired
    public GandalfRoutingSubscriber(GandalfProperties gandalfProperties) {
        super();
        this.gandalfProperties = gandalfProperties;
        this.mapper = new Gson();
        System.out.println(gandalfProperties.getTopics());
        this.initRunnable(this.gandalfProperties.getConnectorName(), this.gandalfProperties.getRoutingSubscriberFrontEndConnection(), this.gandalfProperties.getRoutingSubscriberBackEndConnection());
    }
}