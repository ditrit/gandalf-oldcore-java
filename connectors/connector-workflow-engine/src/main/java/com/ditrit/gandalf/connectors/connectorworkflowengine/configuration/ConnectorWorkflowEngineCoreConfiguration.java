package com.ditrit.gandalf.connectors.connectorworkflowengine.configuration;

import com.ditrit.gandalf.core.connectorcore.routing.ConnectorRoutingSubscriber;
import com.ditrit.gandalf.core.connectorcore.routing.ConnectorRoutingWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf.worker.ConnectorGandalfWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.custom.worker.ConnectorZeebeCustomWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.normative.worker.ConnectorZeebeNormativeWorker;
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
@ComponentScan(basePackages = {"com.ditrit.gandalf.core.connectorcore.worker", "com.ditrit.gandalf.core.clientcore.worker", "com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf", "com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe"})
@Order
public class ConnectorWorkflowEngineCoreConfiguration {

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
        switch(targetType) {
            case "zeebe":
                normativeWorker = (ConnectorZeebeNormativeWorker) context.getBean("normativeWorker");
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
    }
}
