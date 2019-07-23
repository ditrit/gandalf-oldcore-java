package com.orness.gandalf.core.connector.connectorworkflowengineservice.gandalf.config;

import com.orness.gandalf.core.connector.connectorworkflowengineservice.gandalf.communication.command.WorkerWorkflowEngineZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
@Order(1)
public class ConnectorWorkflowEngineServiceConfiguration {

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
    public void workerWorkflowEngineCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");

        WorkerWorkflowEngineZeroMQ workerWorkflowEngineZeroMQ = (WorkerWorkflowEngineZeroMQ) context.getBean("workerWorkflowEngineZeroMQ");
        taskExecutor.execute(workerWorkflowEngineZeroMQ);
    }
}

