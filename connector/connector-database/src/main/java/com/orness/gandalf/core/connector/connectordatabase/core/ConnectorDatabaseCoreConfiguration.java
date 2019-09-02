package com.orness.gandalf.core.connector.connectordatabase.core;

import com.orness.gandalf.core.module.gandalfmodule.worker.ConnectorGandalfWorker;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
//TODO
//@ComponentScan(basePackages = {"com.orness.gandalf.core.module.gandalfmodule", "com.orness.gandalf.core.module.databasemodule", "com.orness.gandalf.core.module.h2module"})
@Order
public class ConnectorDatabaseCoreConfiguration {

    @Autowired
    private ApplicationContext context;

    @Value("spring.profiles.active")
    private String profile;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(10);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Bean
    public void connectorGandalfWorker() {
        RunnableWorkerZeroMQ gandalfWorker = (ConnectorGandalfWorker) context.getBean("gandalfWorker");
        this.taskExecutor().execute(gandalfWorker);
    }

    @Bean
    public void connectorNormativeWorker() {
        RunnableWorkerZeroMQ normativeWorker = null;
        switch(profile) {
            case "h2":
                //normativeWorker = (ConnectorH2NormativeWorker) context.getBean("normativeWorker");
                break;
            default:
                break;
        }
        this.taskExecutor().execute(normativeWorker);
    }

    @Bean
    public void connectorCustomWorker() {
        RunnableWorkerZeroMQ cutomWorker = null;
        switch(profile) {
            case "h2":
                //cutomWorker = (ConnectorCustomArtifactCustomWorker) context.getBean("cutomWorker");
                break;
            default:
                break;
        }
        this.taskExecutor().execute(cutomWorker);
    }
}
