package com.ditrit.gandalf.aggregator.aggregatorgandalf.initialization;

import com.ditrit.gandalf.core.aggregatorcore.properties.AggregatorProperties;
import com.ditrit.gandalf.core.aggregatorcore.service.AggregatorClientService;
import com.ditrit.gandalf.core.aggregatorcore.service.AggregatorListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.zeromq.ZMsg;

@Configuration
public class AggregatorGandalfInitialization {

    private ApplicationContext context;
    private AggregatorProperties aggregatorProperties;

    @Autowired
    public AggregatorGandalfInitialization(ApplicationContext context, AggregatorProperties aggregatorProperties) {
        this.context = context;
        this.aggregatorProperties = aggregatorProperties;
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(10);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Bean
    public void gandalfAggregatorClientService() {
        AggregatorClientService gandalfAggregatorClientService = (AggregatorClientService) context.getBean("aggregatorClientService");
        if(gandalfAggregatorClientService != null) {
            this.taskExecutor().execute(gandalfAggregatorClientService);
            this.initialize(gandalfAggregatorClientService);
        }
    }

    @Bean
    public void gandalfAggregatorListenerService() {
        AggregatorListenerService gandalfAggregatorListenerService = (AggregatorListenerService) context.getBean("aggregatorListenerService");
        if(gandalfAggregatorListenerService != null) {
            this.taskExecutor().execute(gandalfAggregatorListenerService);
        }
    }

    private void initialize(AggregatorClientService aggregatorClientService) {
        ZMsg response = aggregatorClientService.sendRequest("configuration");
        Object[] responseConnections =  response.toArray();
    }
}
