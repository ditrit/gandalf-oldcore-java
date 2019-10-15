package com.ditrit.gandalf.connectors.connectororchestrator.configuration;

import com.ditrit.gandalf.core.connectorcore.connector.ConnectorSubscriber;
import com.ditrit.gandalf.core.connectorcore.connector.ConnectorWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.custom.worker.ConnectorCustomOrchestratorCustomWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.standard.worker.ConnectorCustomOrchestratorStandardWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf.worker.ConnectorGandalfWorker;
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
@ComponentScan(basePackages = {"com.ditrit.gandalf.core.connectorcore", "com.ditrit.gandalf.library.gandalfclient", "com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf", "com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator"})
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
        ConnectorGandalfWorker gandalfWorkerCommand = (ConnectorGandalfWorker) context.getBean("gandalfWorker");
        if(gandalfWorkerCommand != null) {
            this.taskExecutor().execute(gandalfWorkerCommand);
        }
    }

    @Bean
    public void connectorStandardWorker() {
        RunnableWorkerZeroMQ standardWorker = null;
        switch(profile) {
            case "custom-orchestrator":
                standardWorker = (ConnectorCustomOrchestratorStandardWorker) context.getBean("standardWorker");
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
