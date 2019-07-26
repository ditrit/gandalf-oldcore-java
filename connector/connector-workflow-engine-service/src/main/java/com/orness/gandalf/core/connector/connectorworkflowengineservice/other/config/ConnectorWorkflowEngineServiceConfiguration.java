/*
package com.orness.gandalf.core.connector.connectorworkflowengineservice.other.config;

import com.orness.gandalf.core.connector.connectorworkflowengineservice.other.communication.command.WorkflowEngineWorkerZeroMQ;
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

        WorkflowEngineWorkerZeroMQ workflowEngineWorkerZeroMQ = (WorkflowEngineWorkerZeroMQ) context.getBean("workflowEngineWorkerZeroMQ");
        taskExecutor.execute(workflowEngineWorkerZeroMQ);
    }
}

*/
