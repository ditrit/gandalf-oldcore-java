package com.ditrit.gandalf.worker.workergandalf.backup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import worker.Worker;

@Configuration
@ComponentScan(basePackages = {"com.ditrit.gandalf.core.workercore"})
@Order
public class WorkerGandalfConfiguration {

    //@Value("${${instance.name}.connectors.${connector.type}.${connector.name}.target.type}")
    //private String targetType;

    private ApplicationContext context;
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    public WorkerGandalfConfiguration(ApplicationContext context, ThreadPoolTaskExecutor taskExecutor) {
        this.context = context;
        this.taskExecutor = taskExecutor;
    }

    @Bean
    public void gandalfWorker() {
        Worker gandalfConnectorCommand = (Worker) context.getBean("worker");
        if(gandalfConnectorCommand != null) {
            this.taskExecutor.execute(gandalfConnectorCommand);
        }
    }


}
