package com.ditrit.gandalf.cluster.clustergandalf.configuration;

import com.ditrit.gandalf.core.clustercore.cluster.ClusterCommand;
import com.ditrit.gandalf.core.clustercore.cluster.ClusterEvent;
import com.ditrit.gandalf.core.clustercore.worker.ClusterCapture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan(basePackages = {"com.ditrit.gandalf.core.clustercore"})
@Order
public class ClusterGandalfConfiguration {

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
    public void gandalfClusterCommand() {
        ClusterCommand gandalfClusterCommand = (ClusterCommand) context.getBean("clusterCommand");
        this.taskExecutor().execute(gandalfClusterCommand);
    }

    @Bean
    public void gandalfClusterEvent() {
        ClusterEvent gandalfClusterEvent = (ClusterEvent) context.getBean("clusterEvent");
        this.taskExecutor().execute(gandalfClusterEvent);
    }

    @Bean
    public void gandalfClusterCapture() {
        ClusterCapture gandalfClusterCapture = (ClusterCapture) context.getBean("clusterCapture");
        this.taskExecutor().execute(gandalfClusterCapture);
    }
}
