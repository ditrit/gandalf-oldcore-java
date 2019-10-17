package com.ditrit.gandalf.cluster.clustergandalf.configuration;

import com.ditrit.gandalf.core.clustercore.cluster.GandalfBrokerZeroMQ;
import com.ditrit.gandalf.core.clustercore.cluster.GandalfProxyZeroMQ;
import com.ditrit.gandalf.core.clustercore.worker.CaptureWorker;
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
        GandalfBrokerZeroMQ gandalfBrokerZeroMQ = (GandalfBrokerZeroMQ) context.getBean("gandalfBroker");
        this.taskExecutor().execute(gandalfBrokerZeroMQ);
    }

    @Bean
    public void gandalClusterEvent() {
        GandalfProxyZeroMQ gandalfProxyZeroMQ = (GandalfProxyZeroMQ) context.getBean("gandalfProxy");
        this.taskExecutor().execute(gandalfProxyZeroMQ);
    }

    @Bean
    public void gandalfCaptureWorker() {
        CaptureWorker gandalfCaptureWorker = (CaptureWorker) context.getBean("gandalfCapture");
        this.taskExecutor().execute(gandalfCaptureWorker);
    }

}
