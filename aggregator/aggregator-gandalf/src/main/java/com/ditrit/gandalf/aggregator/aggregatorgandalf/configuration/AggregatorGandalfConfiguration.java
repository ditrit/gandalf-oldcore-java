package com.ditrit.gandalf.aggregator.aggregatorgandalf.configuration;

import com.ditrit.gandalf.core.aggregatorcore.aggregator.AggregatorSubscriber;
import com.ditrit.gandalf.core.aggregatorcore.aggregator.AggregatorWorker;
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
    public void gandalfAggregatorWorker() {
        AggregatorWorker gandalfAggregatorWorker = (AggregatorWorker) context.getBean("aggregatorWorker");
        if(gandalfAggregatorWorker != null) {
            this.taskExecutor().execute(gandalfAggregatorWorker);
        }
    }

    @Bean
    public void gandalAggregatorSubscriber() {
        AggregatorSubscriber gandalAggregatorSubscriber = (AggregatorSubscriber) context.getBean("aggregatorSubscriber");
        if(gandalAggregatorSubscriber != null) {
            this.taskExecutor().execute(gandalAggregatorSubscriber);
        }
    }
}
