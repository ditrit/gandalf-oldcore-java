package com.ditrit.gandalf.aggregator.aggregatorgandalf.configuration;

import com.ditrit.gandalf.core.aggregatorcore.aggregator.AggregatorEvent;
import com.ditrit.gandalf.core.aggregatorcore.aggregator.AggregatorCommand;
import com.ditrit.gandalf.core.aggregatorcore.service.AggregatorClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan(basePackages = {"com.ditrit.gandalf.core.aggregatorcore"})
@Order
public class AggregatorGandalfConfiguration {

    @Autowired
    private ApplicationContext context;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(10);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Bean
    public void gandalfAggregatorCommand() {
        AggregatorCommand gandalfAggregatorCommand = (AggregatorCommand) context.getBean("aggregatorCommand");
        if(gandalfAggregatorCommand != null) {
            this.taskExecutor().execute(gandalfAggregatorCommand);
        }
    }

    @Bean
    public void gandalAggregatorEvent() {
        AggregatorEvent gandalAggregatorEvent = (AggregatorEvent) context.getBean("aggregatorEvent");
        if(gandalAggregatorEvent != null) {
            this.taskExecutor().execute(gandalAggregatorEvent);
        }
    }

    @Bean
    public void gandalfAggregatorService() {
        AggregatorClientService gandalfAggregatorClientService = (AggregatorClientService) context.getBean("aggregatorService");
        if(gandalfAggregatorClientService != null) {
            this.taskExecutor().execute(gandalfAggregatorClientService);
        }
    }
}
