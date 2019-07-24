package com.orness.gandalf.core.module.connectormodule.gandalf.communication.event;

import com.orness.gandalf.core.module.zeromqmodule.event.domain.EventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.publisher.PublisherZeroMQ;

public class GandalfPublisherEvent extends PublisherZeroMQ {

    public GandalfPublisherEvent(String connection) {
        super(connection);
    }

    public void sendEvent(String topic, String typeEvent, String event) {
        EventZeroMQ.publishEvent(this.publisher, topic, typeEvent, event);
    }
}
