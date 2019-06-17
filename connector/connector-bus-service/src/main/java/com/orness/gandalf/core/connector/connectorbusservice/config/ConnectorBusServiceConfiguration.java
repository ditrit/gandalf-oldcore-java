package com.orness.gandalf.core.connector.connectorbusservice.config;

import com.orness.gandalf.core.connector.connectorbusservice.communication.command.WorkerBusZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
@Order(2)
public class ConnectorBusServiceConfiguration {

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

        WorkerBusZeroMQ workerBusZeroMQ = (WorkerBusZeroMQ) context.getBean("workerBusZeroMQ");
        taskExecutor.execute(workerBusZeroMQ);
    }
}

