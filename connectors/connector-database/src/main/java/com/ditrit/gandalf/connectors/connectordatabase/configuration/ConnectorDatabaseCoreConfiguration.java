package com.ditrit.gandalf.connectors.connectordatabase.configuration;

import com.ditrit.gandalf.core.connectorcore.aggregator.ConnectorAggregatorSubscriber;
import com.ditrit.gandalf.core.connectorcore.aggregator.ConnectorAggregatorWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf.worker.ConnectorGandalfWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodeh2.normative.worker.ConnectorH2NormativeWorker;
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
@ComponentScan(basePackages = {"com.ditrit.gandalf.core.connectorcore", "com.ditrit.gandalf.library.gandalfworkerclient", "com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf", "com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodeh2"})
@Order
public class ConnectorDatabaseCoreConfiguration {

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
        ConnectorGandalfWorker gandalfWorker = (ConnectorGandalfWorker) context.getBean("gandalfWorker");
        if(gandalfWorker != null) {
            this.taskExecutor().execute(gandalfWorker);
        }
    }

    @Bean
    public void connectorNormativeWorker() {
        RunnableWorkerZeroMQ normativeWorker = null;
        switch(profile) {
            case "h2":
                normativeWorker = (ConnectorH2NormativeWorker) context.getBean("normativeWorker");
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
            case "h2":
                //cutomWorker = (ConnectorCustomArtifactCustomWorker) context.getBean("customWorker");
                break;
            default:
                break;
        }
        if(cutomWorker != null) {
            this.taskExecutor().execute(cutomWorker);
        }
    }
}
