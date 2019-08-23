package com.orness.gandalf.core.test.testworker.core;

import com.orness.gandalf.core.test.testzeromq.gandalf.GandalfProperties;
import com.orness.gandalf.core.test.testzeromq.gandalf.GandalfRoutingSubscriber;
import com.orness.gandalf.core.test.testzeromq.gandalf.GandalfRoutingWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@Order
public class WorkerConfiguration {

    private ApplicationContext context;
    private GandalfProperties gandalfProperties;
    private GandalfRoutingWorker gandalfWorker;

    @Autowired
    public WorkerConfiguration(ApplicationContext context, GandalfProperties gandalfProperties, GandalfRoutingWorker gandalfWorker) {
        this.context = context;
        this.gandalfProperties = gandalfProperties;
        this.gandalfWorker = gandalfWorker;
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
    public void workerCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        taskExecutor.execute(this.gandalfWorker);
    }

    @Bean
    public void subscriberEvent() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        GandalfRoutingSubscriber gandalfSubscriber = new GandalfRoutingSubscriber("TestSub", this.gandalfProperties.getSubscriberFrontEndConnection(), this.gandalfProperties.getWorkerBackEndConnection(), "test");

        taskExecutor.execute(this.gandalfSubscriber);
    }
}
