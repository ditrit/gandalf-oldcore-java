package com.orness.gandalf.core.module.gandalfmodule.communication.event;

import com.orness.gandalf.core.module.gandalfmodule.properties.GandalfProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GandalfSubscriberEventService {

    private GandalfProperties gandalfProperties;
    private ThreadPoolTaskExecutor taskExecutor;
    private Map<String, GandalfSubscriberEvent>  mapTopicSubscriber;

    @Autowired
    public GandalfSubscriberEventService(GandalfProperties gandalfProperties, ThreadPoolTaskExecutor taskExecutor) {
        this.gandalfProperties = gandalfProperties;
        this.taskExecutor = taskExecutor;
        this.mapTopicSubscriber = new HashMap<>();
    }

    public void addInstanceByTopic(String topic) {
        GandalfSubscriberEvent gandalfSubscriberEvent = new GandalfSubscriberEvent(topic, this.gandalfProperties.getSubscriber(), this.gandalfProperties.getClientEvent(), this.gandalfProperties.getWorkerEvent());
        this.mapTopicSubscriber.put(topic, gandalfSubscriberEvent);
        this.taskExecutor.execute(gandalfSubscriberEvent);
    }

    public void addWorkerByTopic(String topic, GandalfWorkerEvent gandalfWorkerEvent) {
        this.mapTopicSubscriber.get(topic).addWorker(gandalfWorkerEvent);
    }

    public void addInstanceWorkerByTopic(String topic, GandalfWorkerEvent gandalfWorkerEvent) {
        this.addInstanceByTopic(topic);
        this.addWorkerByTopic(topic, gandalfWorkerEvent);
    }

}
