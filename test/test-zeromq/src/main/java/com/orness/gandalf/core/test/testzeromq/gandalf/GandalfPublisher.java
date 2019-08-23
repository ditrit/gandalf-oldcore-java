package com.orness.gandalf.core.test.testzeromq.gandalf;


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
        this.init(gandalfProperties.getPublisherBackEndConnection());
    }

    public void sendEvent(String topic, String typeEvent, String event) {
        this.backEndPublisher.sendMore(topic);
        this.backEndPublisher.sendMore(typeEvent);
        this.backEndPublisher.send(event);
    }
}
