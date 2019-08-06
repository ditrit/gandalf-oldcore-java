package com.orness.gandalf.core.module.gandalfmodule.communication.event;

import com.orness.gandalf.core.module.gandalfmodule.properties.properties.GandalfProperties;
import com.orness.gandalf.core.module.zeromqmodule.event.domain.EventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.publisher.PublisherZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

public class GandalfPublisherEvent extends PublisherZeroMQ {

    private GandalfProperties gandalfProperties;

    @Autowired
    public GandalfPublisherEvent(GandalfProperties gandalfProperties) {
        super();
        this.gandalfProperties = gandalfProperties;
        this.connect(gandalfProperties.getPublisher());
    }

    public void sendEvent(String topic, String typeEvent, String event) {
        EventZeroMQ.publishEvent(this.publisher, topic, typeEvent, event);
    }
}
