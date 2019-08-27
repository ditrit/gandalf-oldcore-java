package com.orness.gandalf.core.test.testworker.core;

import com.orness.gandalf.core.test.testworker.properties.GandalfProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@Order
public class WorkerConfiguration {

    private GandalfProperties gandalfProperties;
    private GandalfRoutingWorker gandalfWorker;
    private GandalfRoutingSubscriber gandalfSubscriber;

    @Autowired
    public WorkerConfiguration(GandalfProperties gandalfProperties, GandalfRoutingWorker gandalfWorker, GandalfRoutingSubscriber gandalfSubscriber) {
        this.gandalfProperties = gandalfProperties;
        this.gandalfWorker = gandalfWorker;
        this.gandalfSubscriber = gandalfSubscriber;
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(10);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Bean
    public void workerCommand() {
        this.taskExecutor().execute(this.gandalfWorker);
    }

    @Bean
    public void subscriberEvent() {
        this.taskExecutor().execute(this.gandalfSubscriber);
    }
}
