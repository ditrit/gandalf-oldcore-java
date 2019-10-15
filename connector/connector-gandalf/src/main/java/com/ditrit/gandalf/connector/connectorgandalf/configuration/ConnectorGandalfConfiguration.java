package com.ditrit.gandalf.connector.connectorgandalf.configuration;

import com.ditrit.gandalf.core.connectorcore.connector.ConnectorService;
import com.ditrit.gandalf.core.connectorcore.connector.ConnectorSubscriber;
import com.ditrit.gandalf.core.connectorcore.connector.ConnectorWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan(basePackages = {"com.ditrit.gandalf.core.connectorcore"})
@Order
public class ConnectorGandalfConfiguration {

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
    public void gandalfConnectorWorker() {
        ConnectorWorker gandalfConnectorWorker = (ConnectorWorker) context.getBean("connectorWorker");
        if(gandalfConnectorWorker != null) {
            this.taskExecutor().execute(gandalfConnectorWorker);
        }
    }

    @Bean
    public void gandalfConnectorSubscriber() {
        ConnectorSubscriber gandalfConnectorSubscriber = (ConnectorSubscriber) context.getBean("connectorSubscriber");
        if(gandalfConnectorSubscriber != null) {
            this.taskExecutor().execute(gandalfConnectorSubscriber);
        }
    }

    @Bean
    public void gandalfConnectorService() {
        ConnectorService gandalfConnectorService = (ConnectorService) context.getBean("connectorService");
        if(gandalfConnectorService != null) {
            this.taskExecutor().execute(gandalfConnectorService);
        }
    }

  /*  @Bean
    public void connectorGandalfWorker() {
        ConnectorGandalfWorker gandalfWorker = (ConnectorGandalfWorker) context.getBean("gandalfWorker");
        if(gandalfWorker != null) {
            this.taskExecutor().execute(gandalfWorker);
        }
    }

    @Bean
    public void connectorStandardWorker() {
        RunnableWorkerZeroMQ standardWorker = null;
        switch(targetType) {
            case "zeebe":
                standardWorker = (ConnectorZeebeStandardWorker) context.getBean("standardWorker");
                break;
            default:
                break;
        }
        if(standardWorker != null) {
            this.taskExecutor().execute(standardWorker);
        }
    }

    @Bean
    public void connectorCustomWorker() {
        RunnableWorkerZeroMQ cutomWorker = null;
        switch(targetType) {
            case "zeebe":
                cutomWorker = (ConnectorZeebeCustomWorker) context.getBean("customWorker");
                break;
            default:
                break;
        }
        if(cutomWorker != null) {
            this.taskExecutor().execute(cutomWorker);
        }
    }*/
}
