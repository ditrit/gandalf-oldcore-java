package com.orness.gandalf.core.connector.connectorgandalfservice.gandalf.config;

import com.orness.gandalf.core.module.connectormodule.gandalf.communication.command.GandalfWorkerCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ConnectorGandalfConfiguration {

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
    public void workerGandalfCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        taskExecutor.execute((GandalfWorkerCommand) context.getBean("gandalfWorkerCommand"));
    }
}
