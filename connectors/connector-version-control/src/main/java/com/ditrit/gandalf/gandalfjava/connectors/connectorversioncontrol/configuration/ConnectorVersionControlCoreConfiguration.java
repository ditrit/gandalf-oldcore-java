package com.ditrit.gandalf.gandalfjava.connectors.connectorversioncontrol.configuration;

import com.ditrit.gandalf.core.connectorcore.connector.ConnectorEvent;
import com.ditrit.gandalf.core.connectorcore.connector.ConnectorCommand;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf.worker.ConnectorGandalfWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.custom.worker.ConnectorGitlabCustomWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.standard.worker.ConnectorGitlabStandardWorker;
import com.ditrit.gandalf.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan(basePackages = {"com.ditrit.gandalf.core.connectorcore", "com.ditrit.gandalf.library.gandalfclient", "com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf", "com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab"})
@Order
public class ConnectorVersionControlCoreConfiguration {

    @Autowired
    private ApplicationContext context;
    @Value("${${instance.name}.connectors.${connector.type}.${connector.name}.target.type}")
    private String profile;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(10);
        pool.setMaxPoolSize(20);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Bean
    public void connectorAggregatorWorker() {
        ConnectorCommand connectorWorker = (ConnectorCommand) context.getBean("aggregatorWorker");
        if(connectorWorker != null) {
            this.taskExecutor().execute(connectorWorker);
        }
    }

    @Bean
    public void connectorAggregatorSubscriber() {
        ConnectorEvent connectorSubscriber = (ConnectorEvent) context.getBean("aggregatorSubscriber");
        if(connectorSubscriber != null) {
            this.taskExecutor().execute(connectorSubscriber);
        }
    }

    @Bean
    public void connectorGandalfWorker() {
        ConnectorGandalfWorker gandalfWorker = (ConnectorGandalfWorker) context.getBean("gandalfWorker");
        if(gandalfWorker != null) {
            this.taskExecutor().execute(gandalfWorker);
        }
    }

    @Bean
    public void connectorStandardWorker() {
        RunnableWorkerZeroMQ standardWorker = null;
        switch(profile) {
            case "gitlab":
                standardWorker = (ConnectorGitlabStandardWorker) context.getBean("standardWorker");
                System.out.println(standardWorker);
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
        switch(profile) {
            case "gitlab":
                cutomWorker = (ConnectorGitlabCustomWorker) context.getBean("customWorker");
                break;
            default:
                break;
        }
        if(cutomWorker != null) {
            this.taskExecutor().execute(cutomWorker);
        }
    }
}
