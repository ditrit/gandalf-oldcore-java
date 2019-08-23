package com.orness.gandalf.core.test.testcommand.core;

import com.orness.gandalf.core.test.testcommand.properties.GandalfProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@Order
public class CommandConfiguration {

    private GandalfProperties gandalfProperties;

    public CommandConfiguration(GandalfProperties gandalfProperties) {
        this.gandalfProperties = gandalfProperties;
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
    public void gandalfWorkerCommand() {
        GandalfWorkerStartWorker gandalfWorkerStartWorker = new GandalfWorkerStartWorker(this.gandalfProperties.getRoutingWorkerBackEndConnection());
        this.taskExecutor().execute(gandalfWorkerStartWorker);
    }

    @Bean
    public void gandalfWorkerEvent() {
        GandalfWorkerStartEvent gandalfWorkerStartEvent = new GandalfWorkerStartEvent(this.gandalfProperties.getRoutingSubscriberBackEndConnection());
        this.taskExecutor().execute(gandalfWorkerStartEvent);
    }
}
