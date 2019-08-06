package com.orness.gandalf.core.module.gandalfmodule.manager;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.gandalfmodule.communication.event.GandalfPublisherEvent;
import com.orness.gandalf.core.module.gandalfmodule.properties.properties.GandalfProperties;
import com.orness.gandalf.core.module.messagemodule.gandalf.domain.GandalfEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GandalfConnectorManager {

    private GandalfProperties gandalfProperties;
    private GandalfPublisherEvent gandalfPublisherEvent;
    private Gson mapper;

    @Autowired
    public GandalfConnectorManager(GandalfProperties gandalfProperties) {
        this.gandalfProperties = gandalfProperties;
        //TODO WUTTT
        this.gandalfPublisherEvent = new GandalfPublisherEvent(this.gandalfProperties);
        this.mapper = new Gson();
    }

    public void start() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void stop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void publish(GandalfEvent gandalfEvent) {
        this.gandalfPublisherEvent.sendEvent(gandalfEvent.getTopic(), gandalfEvent.getTypeEvent(), gandalfEvent.getEvent());
    }

    public void subscribe(String topic) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void unsubscribe(String topic) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
