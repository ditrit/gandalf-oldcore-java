package com.orness.gandalf.core.job.registerjob.config;

import com.orness.gandalf.core.job.registerjob.properties.RegisterJobProperties;
import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class RegisterJobConfiguration {

    private RegisterJobProperties registerJobProperties;

    @Autowired
    public RegisterJobConfiguration(RegisterJobProperties registerJobProperties) {
        this.registerJobProperties = registerJobProperties;
    }

    @Bean
    public ZeebeClient zeebe() {
        //Client
        ZeebeClient zeebeClient = ZeebeClient.newClientBuilder()
                .brokerContactPoint(this.registerJobProperties.getEndPointConnection())
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
