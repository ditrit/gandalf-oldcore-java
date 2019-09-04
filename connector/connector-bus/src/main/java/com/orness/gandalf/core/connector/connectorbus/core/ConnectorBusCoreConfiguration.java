package com.orness.gandalf.core.connector.connectorbus.core;

import com.orness.gandalf.core.module.clientcore.GandalfClient;
import com.orness.gandalf.core.module.gandalfmodule.worker.ConnectorGandalfWorker;
import com.orness.gandalf.core.module.kafkamodule.custom.worker.ConnectorKafkaCustomWorker;
import com.orness.gandalf.core.module.kafkamodule.normative.worker.ConnectorKafkaNormativeWorker;
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
@ComponentScan(basePackages = {"com.orness.gandalf.core.module.clientcore", "com.orness.gandalf.core.module.gandalfmodule", "com.orness.gandalf.core.module.kafkamodule"})
@Order
public class ConnectorBusCoreConfiguration {

    @Autowired
    private ApplicationContext context;

    @Value("${spring.profiles.active}")
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
    public void connectorGandalfClient() {
        GandalfClient gandalfClient = (GandalfClient) context.getBean("gandalfClient");
        this.taskExecutor().execute(gandalfClient.getClientCommand());
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
            case "kafka":
                normativeWorker = (ConnectorKafkaNormativeWorker) context.getBean("normativeWorker");
                break;
            default:
                break;
        }
        this.taskExecutor().execute(normativeWorker);
    }

    @Bean
    public void connectorCustomWorker() {
        RunnableWorkerZeroMQ customWorker = null;
        switch(profile) {
            case "kafka":
                customWorker = (ConnectorKafkaCustomWorker) context.getBean("customWorker");
                break;
            default:
                break;
        }
        this.taskExecutor().execute(customWorker);
    }
}
