package com.ditrit.gandalf.connectors.connectorartifact.configuration;

import com.ditrit.gandalf.core.connectorcore.routing.ConnectorRoutingSubscriber;
import com.ditrit.gandalf.core.connectorcore.routing.ConnectorRoutingWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.custom.worker.ConnectorCustomArtifactCustomWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.normative.worker.ConnectorCustomArtifactNormativeWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf.worker.ConnectorGandalfWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.custom.worker.ConnectorNexusCustomWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.normative.worker.ConnectorNexusNormativeWorker;
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
@ComponentScan(basePackages = {"com.ditrit.gandalf.core.connectorcore", "com.ditrit.gandalf.core.clientcore.worker", "com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf", "com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact"})
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
    public void connectorRoutingWorker() {
        ConnectorRoutingWorker connectorRoutingWorker = (ConnectorRoutingWorker) context.getBean("routingWorker");
        if(connectorRoutingWorker != null) {
            this.taskExecutor().execute(connectorRoutingWorker);
        }
    }

    @Bean
    public void connectorRoutingSubscriber() {
        ConnectorRoutingSubscriber connectorRoutingSubscriber = (ConnectorRoutingSubscriber) context.getBean("routingSubscriber");
        if(connectorRoutingSubscriber != null) {
            this.taskExecutor().execute(connectorRoutingSubscriber);
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
    public void connectorNormativeWorker() {
        RunnableWorkerZeroMQ normativeWorker = null;
        switch(profile) {
            case "custom-artifact":
                normativeWorker = (ConnectorCustomArtifactNormativeWorker) context.getBean("normativeWorker");
                break;
            case "nexus":
                normativeWorker = (ConnectorNexusNormativeWorker) context.getBean("normativeWorker");
                break;
            default:
                break;
        }
        if(normativeWorker != null) {
            this.taskExecutor().execute(normativeWorker);
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
