package com.ditrit.gandalf.worker.workergandalf.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import service.WorkerClientService;
import worker.Worker;

@Configuration
@ComponentScan(basePackages = {"com.ditrit.gandalf.core.workercore"})
@Order
public class WorkerGandalfConfiguration {

    @Autowired
    private ApplicationContext context;
    @Value("${${instance.name}.connectors.${connector.type}.${connector.name}.target.type}")
    private String targetType;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(10);
        pool.setMaxPoolSize(20);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Bean
    public void gandalfWorker() {
        Worker gandalfConnectorCommand = (Worker) context.getBean("connectorCommand");
        if(gandalfConnectorCommand != null) {
            this.taskExecutor().execute(gandalfConnectorCommand);
        }
    }

    @Bean
    public void gandalfWorkerClientService() {
        WorkerClientService gandalfWorkerClientService = (WorkerClientService) context.getBean("workerClientService");
        if(gandalfWorkerClientService != null) {
            this.taskExecutor().execute(gandalfWorkerClientService);
        }
    }
}
