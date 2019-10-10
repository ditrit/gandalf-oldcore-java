package com.ditrit.gandalf.connectors.connectororchestrator.configuration;

import com.ditrit.gandalf.core.connectorcore.aggregator.ConnectorAggregatorSubscriber;
import com.ditrit.gandalf.core.connectorcore.aggregator.ConnectorAggregatorWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.custom.worker.ConnectorCustomOrchestratorCustomWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.normative.worker.ConnectorCustomOrchestratorNormativeWorker;
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
@ComponentScan(basePackages = {"com.ditrit.gandalf.core.connectorcore", "com.ditrit.gandalf.core.clientcore.worker", "com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf", "com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator"})
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
        ConnectorAggregatorWorker connectorAggregatorWorker = (ConnectorAggregatorWorker) context.getBean("aggregatorWorker");
        if(connectorAggregatorWorker != null) {
            this.taskExecutor().execute(connectorAggregatorWorker);
        }
    }

    @Bean
    public void connectorRoutingSubscriber() {
        ConnectorAggregatorSubscriber connectorAggregatorSubscriber = (ConnectorAggregatorSubscriber) context.getBean("aggregatorSubscriber");
        if(connectorAggregatorSubscriber != null) {
            this.taskExecutor().execute(connectorAggregatorSubscriber);
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
