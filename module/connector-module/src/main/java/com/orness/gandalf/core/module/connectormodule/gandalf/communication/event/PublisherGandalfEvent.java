package com.orness.gandalf.core.module.connectormodule.gandalf.communication.event;

import com.orness.gandalf.core.module.zeromqmodule.event.domain.EventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.publisher.PublisherZeroMQ;

public class PublisherGandalfEvent extends PublisherZeroMQ {

    public PublisherGandalfEvent(String connection) {
        super(connection);
    }

    private void sendEvent(String topic, String typeEvent, String event) {
        EventZeroMQ.publishEvent(publisher, topic, typeEvent, event);
    }
}
