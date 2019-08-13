package com.orness.gandalf.core.module.gandalfmodule.communication.event;

import com.orness.gandalf.core.module.gandalfmodule.properties.GandalfProperties;
import com.orness.gandalf.core.module.zeromqmodule.event.worker.RunnableWorkerEventZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO LOAD BY CONFIGURATION
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
        //this.addWorkerByTopic(topic, gandalfSubscriberEvent);
        this.mapTopicSubscriber.put(topic, gandalfSubscriberEvent);
        this.taskExecutor.execute(gandalfSubscriberEvent);
    }

    public void addWorkerByTopic(String topic, RunnableWorkerEventZeroMQ runnableWorkerEventZeroMQ) {
        runnableWorkerEventZeroMQ = this.mapTopicSubscriber.get(topic).instantiateWorker(runnableWorkerEventZeroMQ);
        this.taskExecutor.execute(runnableWorkerEventZeroMQ);
    }

    public void addInstanceByTopicAndWorker(String topic, List<RunnableWorkerEventZeroMQ> runnableWorkerEventZeroMQList) {
        GandalfSubscriberEvent gandalfSubscriberEvent = new GandalfSubscriberEvent(topic, this.gandalfProperties.getSubscriber(), this.gandalfProperties.getClientEvent(), this.gandalfProperties.getWorkerEvent());
        this.mapTopicSubscriber.put(topic, gandalfSubscriberEvent);

        //WORKERS
        runnableWorkerEventZeroMQList = gandalfSubscriberEvent.instantiateWorkers(runnableWorkerEventZeroMQList);
        for(RunnableWorkerEventZeroMQ runnableWorkerEventZeroMQ : runnableWorkerEventZeroMQList) {
            taskExecutor.execute(runnableWorkerEventZeroMQ);
        }

        //Subscriber
        taskExecutor.execute(gandalfSubscriberEvent);
    }
}
