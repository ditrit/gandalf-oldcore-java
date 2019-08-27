package com.orness.gandalf.core.test.testclient.core;

import com.orness.gandalf.core.test.testclient.properties.GandalfProperties;
import com.orness.gandalf.core.test.testzeromq.event.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GandalfPublisher extends Publisher {

    private GandalfProperties gandalfProperties;

    @Autowired
    public GandalfPublisher(GandalfProperties gandalfProperties) {
        super();
        this.gandalfProperties = gandalfProperties;
        this.init(this.gandalfProperties.getConnectorName(), this.gandalfProperties.getPublisherBackEndConnection());
    }

    public void sendEvent(String topic, String event, String payload) {
        this.backEndPublisher.sendMore(topic);
        this.backEndPublisher.sendMore(event);
        this.backEndPublisher.send(payload);
    }
}
