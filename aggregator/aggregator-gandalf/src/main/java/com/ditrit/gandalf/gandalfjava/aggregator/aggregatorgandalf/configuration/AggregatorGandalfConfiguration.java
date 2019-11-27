package com.ditrit.gandalf.gandalfjava.aggregator.aggregatorgandalf.configuration;

import com.ditrit.gandalf.gandalfjava.core.aggregatorcore.aggregator.AggregatorEvent;
import com.ditrit.gandalf.gandalfjava.core.aggregatorcore.aggregator.AggregatorCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan(basePackages = {"com.ditrit.gandalf.gandalfjava.core.aggregatorcore"})
@Order
public class AggregatorGandalfConfiguration {

    private ApplicationContext context;
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    public AggregatorGandalfConfiguration(ApplicationContext context, ThreadPoolTaskExecutor taskExecutor) {
        this.context = context;
        this.taskExecutor = taskExecutor;
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
    public void gandalfAggregatorCommand() {
        AggregatorCommand gandalfAggregatorCommand = (AggregatorCommand) context.getBean("aggregatorCommand");
        if(gandalfAggregatorCommand != null) {
            this.taskExecutor.execute(gandalfAggregatorCommand);
        }
    }

    @Bean
    public void gandalAggregatorEvent() {
        AggregatorEvent gandalAggregatorEvent = (AggregatorEvent) context.getBean("aggregatorEvent");
        if(gandalAggregatorEvent != null) {
            this.taskExecutor.execute(gandalAggregatorEvent);
        }
    }
}
