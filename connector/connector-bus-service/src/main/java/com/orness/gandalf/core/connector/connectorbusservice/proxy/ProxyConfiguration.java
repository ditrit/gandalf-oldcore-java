
package com.orness.gandalf.core.connector.connectorbusservice.proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
@PropertySource(value={"classpath:bootstrap.properties"}, ignoreResourceNotFound = true)
@ComponentScan(basePackages = "com.orness.gandalf.core.connector.connectorbusservice.proxy")
public class ProxyConfiguration {

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(10);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }
}

