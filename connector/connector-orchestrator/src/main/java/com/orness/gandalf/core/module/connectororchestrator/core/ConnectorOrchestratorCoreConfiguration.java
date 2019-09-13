package com.orness.gandalf.core.module.connectororchestrator.core;

import com.orness.gandalf.core.module.connectorcore.routing.ConnectorRoutingSubscriber;
import com.orness.gandalf.core.module.connectorcore.routing.ConnectorRoutingWorker;
import com.orness.gandalf.core.module.customorchestratormodule.custom.worker.ConnectorCustomOrchestratorCustomWorker;
import com.orness.gandalf.core.module.customorchestratormodule.normative.worker.ConnectorCustomOrchestratorNormativeWorker;
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
@ComponentScan(basePackages = {"com.orness.gandalf.core.module.connectorcore", "com.orness.gandalf.core.module.clientcore", "com.orness.gandalf.core.module.gandalfmodule", "com.orness.gandalf.core.module.customorchestratormodule"})
@Order
public class ConnectorOrchestratorCoreConfiguration {

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
        ConnectorGandalfWorker gandalfWorkerCommand = (ConnectorGandalfWorker) context.getBean("gandalfWorker");
        if(gandalfWorkerCommand != null) {
            this.taskExecutor().execute(gandalfWorkerCommand);
        }
    }

    @Bean
    public void connectorNormativeWorker() {
        RunnableWorkerZeroMQ normativeWorker = null;
        switch(profile) {
            case "custom-orchestrator":
                normativeWorker = (ConnectorCustomOrchestratorNormativeWorker) context.getBean("normativeWorker");
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
        switch(profile) {
            case "custom-orchestrator":
                cutomWorker = (ConnectorCustomOrchestratorCustomWorker) context.getBean("customWorker");
                break;
            default:
                break;
        }
        if(cutomWorker != null) {
            this.taskExecutor().execute(cutomWorker);
        }
    }
}
