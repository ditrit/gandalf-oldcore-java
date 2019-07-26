package com.orness.gandalf.core.module.gandalfmodule.manager;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.gandalfmodule.communication.event.GandalfPublisherEvent;
import com.orness.gandalf.core.module.messagemodule.gandalf.domain.GandalfEvent;
import org.springframework.stereotype.Component;

@Component
public class GandalfConnectorManager {

    private GandalfPublisherEvent gandalfPublisherEvent;
    private Gson mapper;
    //TODO FINISH
    public GandalfConnectorManager() {
        this.gandalfPublisherEvent = new GandalfPublisherEvent("");
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

    public void subscribe() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void unsubscribe() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
