package com.orness.gandalf.core.test.javakafka2.sample;

import com.orness.gandalf.core.module.gandalfmodule.communication.event.GandalfSubscriberEventService;
import com.orness.gandalf.core.module.gandalfmodule.factory.GandalfSubscriberServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ThreadConfig {

    @Autowired
    GandalfSubscriberServiceFactory gandalfSubscriberServiceFactory;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(100);
        pool.setMaxPoolSize(100);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Bean
    public GandalfSubscriberEventService gandalfSubscriberEventService() throws Exception {
        return gandalfSubscriberServiceFactory.createInstanceByClass("com.orness.gandalf.core.module.kafkamodule.core.producer.GandalfKafkaSubscriber");
    }

}
