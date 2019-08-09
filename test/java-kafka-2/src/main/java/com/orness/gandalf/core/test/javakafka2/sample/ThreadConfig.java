package com.orness.gandalf.core.test.javakafka2.sample;

import com.orness.gandalf.core.module.gandalfmodule.communication.event.GandalfSubscriberEventFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ThreadConfig {

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(100);
        pool.setMaxPoolSize(100);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Bean
    public GandalfSubscriberEventFactory gandalfSubscriberEventFactory() {
        GandalfSubscriberEventFactory gandalfSubscriberEventFactory = new GandalfSubscriberEventFactory();
        return gandalfSubscriberEventFactory;
    }
}
