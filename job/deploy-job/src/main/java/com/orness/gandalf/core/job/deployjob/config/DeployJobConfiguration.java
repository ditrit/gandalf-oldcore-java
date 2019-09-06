package com.orness.gandalf.core.job.deployjob.config;

import com.orness.gandalf.core.job.deployjob.properties.DeployJobProperties;
import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class DeployJobConfiguration {

    private DeployJobProperties deployJobProperties;

    @Autowired
    public DeployJobConfiguration(DeployJobProperties deployJobProperties) {
        this.deployJobProperties = deployJobProperties;
    }

    @Bean
    public ZeebeClient zeebe() {
        //Client
        ZeebeClient zeebeClient = ZeebeClient.newClientBuilder()
                .brokerContactPoint(this.deployJobProperties.getEndPointConnection())
                .build();
        return zeebeClient;
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(10);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }
}
