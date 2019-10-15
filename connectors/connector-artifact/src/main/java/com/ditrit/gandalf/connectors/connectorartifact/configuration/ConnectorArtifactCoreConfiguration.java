package com.ditrit.gandalf.connectors.connectorartifact.configuration;

import com.ditrit.gandalf.core.connectorcore.connector.ConnectorSubscriber;
import com.ditrit.gandalf.core.connectorcore.connector.ConnectorWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.custom.worker.ConnectorCustomArtifactCustomWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.standard.worker.ConnectorCustomArtifactStandardWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf.worker.ConnectorGandalfWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.custom.worker.ConnectorNexusCustomWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.standard.worker.ConnectorNexusStandardWorker;
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
@ComponentScan(basePackages = {"com.ditrit.gandalf.core.connectorcore", "com.ditrit.gandalf.library.gandalfclient", "com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf", "com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact"})
@Order
public class ConnectorArtifactCoreConfiguration {

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
        ConnectorWorker connectorWorker = (ConnectorWorker) context.getBean("aggregatorWorker");
        if(connectorWorker != null) {
            this.taskExecutor().execute(connectorWorker);
        }
    }

    @Bean
    public void connectorAggregatorSubscriber() {
        ConnectorSubscriber connectorSubscriber = (ConnectorSubscriber) context.getBean("aggregatorSubscriber");
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
            case "custom-artifact":
                standardWorker = (ConnectorCustomArtifactStandardWorker) context.getBean("standardWorker");
                break;
            case "nexus":
                standardWorker = (ConnectorNexusStandardWorker) context.getBean("standardWorker");
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
        RunnableWorkerZeroMQ customWorker = null;
        switch(profile) {
            case "custom-artifact":
                customWorker = (ConnectorCustomArtifactCustomWorker) context.getBean("customWorker");
                break;
            case "nexus":
                customWorker = (ConnectorNexusCustomWorker) context.getBean("customWorker");
                break;
            default:
                break;
        }
        if(customWorker != null) {
            this.taskExecutor().execute(customWorker);
        }
    }
}
