package com.orness.gandalf.core.test.testcommand.core;

import com.orness.gandalf.core.test.testcommand.properties.GandalfProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static com.orness.gandalf.core.test.testzeromq.Constant.WORKER_SERVICE_CLASS_ADMIN;

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
    public void gandalfWorkerEvent() {
        GandalfWorker gandalfWorker = new GandalfWorker(WORKER_SERVICE_CLASS_ADMIN, this.gandalfProperties.getRoutingWorkerBackEndConnection(),  this.gandalfProperties.getRoutingSubscriberBackEndConnection(), this.gandalfProperties.getTopics());
        this.taskExecutor().execute(gandalfWorker);
    }
}
