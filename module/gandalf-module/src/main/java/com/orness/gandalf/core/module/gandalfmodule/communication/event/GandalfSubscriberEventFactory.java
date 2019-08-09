package com.orness.gandalf.core.module.gandalfmodule.communication.event;

import com.orness.gandalf.core.module.gandalfmodule.properties.GandalfProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class GandalfSubscriberEventFactory {

    private GandalfProperties gandalfProperties;
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    public GandalfSubscriberEventFactory(GandalfProperties gandalfProperties, ThreadPoolTaskExecutor taskExecutor) {
        this.gandalfProperties = gandalfProperties;
        this.taskExecutor = taskExecutor;
    }

    public void startInstanceByTopic(String topic) {
        this.taskExecutor.execute(this.createInstanceByTopic(topic));
    }

    private GandalfSubscriberEvent createInstanceByTopic(String topic) {
        GandalfSubscriberEvent gandalfSubscriberEvent = new GandalfSubscriberEvent(topic, this.gandalfProperties);
        gandalfSubscriberEvent.connect();
        return gandalfSubscriberEvent;
    }
}
