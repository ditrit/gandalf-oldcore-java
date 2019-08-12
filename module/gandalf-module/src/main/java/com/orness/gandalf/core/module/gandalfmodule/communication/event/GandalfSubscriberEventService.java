package com.orness.gandalf.core.module.gandalfmodule.communication.event;

import com.orness.gandalf.core.module.gandalfmodule.properties.GandalfProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    //TODO GET WORKERS
    public void addInstanceByTopic(String topic) {
        GandalfSubscriberEvent gandalfSubscriberEvent = new GandalfSubscriberEvent(topic, this.gandalfProperties.getSubscriber(), this.gandalfProperties.getClientEvent(), this.gandalfProperties.getWorkerEvent());
        this.addWorkerByTopic(topic, gandalfSubscriberEvent);
        this.mapTopicSubscriber.put(topic, gandalfSubscriberEvent);
        this.taskExecutor.execute(gandalfSubscriberEvent);
    }

    //TODO GET WORKERS
    private void addWorkerByTopic(String topic, GandalfSubscriberEvent gandalfSubscriberEvent) {
        //TODO
        List<String> workers = new ArrayList<String>();
        for(String worker : workers) {
            //TODO REVOIR CONSTRUCTOR
            GandalfWorkerEvent gandalfWorkerEvent = null;
            try {
                gandalfWorkerEvent = (GandalfWorkerEvent) Class.forName(worker).getDeclaredConstructor().newInstance();
                gandalfSubscriberEvent.addWorker(gandalfWorkerEvent);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
