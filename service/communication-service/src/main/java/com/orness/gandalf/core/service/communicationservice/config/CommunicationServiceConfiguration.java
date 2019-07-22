package com.orness.gandalf.core.service.communicationservice.config;

import com.orness.gandalf.core.service.communicationservice.command.BrokerCommandBean;
import com.orness.gandalf.core.service.communicationservice.event.ProxyEventBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
public class CommunicationServiceConfiguration {

    @Autowired
    private ApplicationContext context;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(10);
        pool.setMaxPoolSize(10);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Bean
    public void brokerCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");

        BrokerCommandBean brokerCommandBean = (BrokerCommandBean) context.getBean("brokerCommandBean");
        taskExecutor.execute(brokerCommandBean);
    }

    @Bean
    public void proxyEvent() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");

        ProxyEventBean proxyEventBean = (ProxyEventBean) context.getBean("proxyEventBean");
        taskExecutor.execute(proxyEventBean);
    }
}

