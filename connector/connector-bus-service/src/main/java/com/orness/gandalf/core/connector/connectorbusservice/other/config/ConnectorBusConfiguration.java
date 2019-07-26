/*
package com.orness.gandalf.core.connector.connectorbusservice.other.config;

import com.orness.gandalf.core.connector.connectorbusservice.other.kafka.communication.command.BusWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@Order(2)
public class ConnectorBusConfiguration {

    @Autowired
    private ApplicationContext context;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(10);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Bean
    public void workerBusCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");

        BusWorkerZeroMQ busWorkerZeroMQ = (BusWorkerZeroMQ) context.getBean("busWorkerZeroMQ");
        taskExecutor.execute(busWorkerZeroMQ);
    }
}

*/
