package com.ditrit.gandalf.connectors.connectorbus.configuration;

import com.ditrit.gandalf.core.connectorcore.aggregator.ConnectorAggregatorSubscriber;
import com.ditrit.gandalf.core.connectorcore.aggregator.ConnectorAggregatorWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf.worker.ConnectorGandalfWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.custom.worker.ConnectorKafkaCustomWorker;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.standard.worker.ConnectorKafkaStandardWorker;
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
@ComponentScan(basePackages = {"com.ditrit.gandalf.core.connectorcore", "com.ditrit.gandalf.library.gandalfworkerclient", "com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf", "com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka"})
@Order
public class ConnectorBusCoreConfiguration {

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
    public void connectorStandardWorker() {
        RunnableWorkerZeroMQ standardWorker = null;
        switch(profile) {
            case "kafka":
                standardWorker = (ConnectorKafkaStandardWorker) context.getBean("standardWorker");
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
            case "kafka":
                customWorker = (ConnectorKafkaCustomWorker) context.getBean("customWorker");
                break;
            default:
                break;
        }
        if(customWorker != null) {
            this.taskExecutor().execute(customWorker);
        }
    }
}
