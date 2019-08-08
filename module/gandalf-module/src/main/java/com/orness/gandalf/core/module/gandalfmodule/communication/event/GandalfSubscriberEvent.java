package com.orness.gandalf.core.module.gandalfmodule.communication.event;

import com.orness.gandalf.core.module.gandalfmodule.properties.GandalfProperties;
import com.orness.gandalf.core.module.zeromqmodule.event.domain.MessageEventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.subscriber.RunnableSubscriberZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class GandalfSubscriberEvent extends RunnableSubscriberZeroMQ {

    @Autowired
    private GandalfProperties gandalfProperties;

    private Map<String, GandalfClientEvent> mapEventWorker; //<EventType, GandalfClientEvent>

    public GandalfSubscriberEvent(String topic) {
        super();
        this.gandalfProperties = gandalfProperties;
        this.mapEventWorker = new HashMap<>();
        this.connect(topic, gandalfProperties.getSubscriber());
    }

    //TODO VOIR SI POST CONSTRCUT
    private void connect(String connection, String topic) {
        this.connect(connection);
        this.subscribe(topic);
    }


    @Override
    protected void sendMessageEventZeroMQ(MessageEventZeroMQ messageEventZeroMQ) {
        GandalfClientEvent gandalfClientEvent =  this.mapEventWorker.get(messageEventZeroMQ.getTypeEvent());
        if(gandalfClientEvent != null) {
            gandalfClientEvent.sendEventCommand(messageEventZeroMQ.getTypeEvent(), messageEventZeroMQ.getEvent());
        }
    }
}
