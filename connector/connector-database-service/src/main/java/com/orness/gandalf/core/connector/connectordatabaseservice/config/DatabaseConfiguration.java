package com.orness.gandalf.core.connector.connectordatabaseservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class DatabaseConfiguration {

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

   /* @Bean
    @ConditionalOnExpression("${module.common:kafka}")
    public void commonWorkerCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        KafkaCommonWorkerCommand commonWorkerCommand = (KafkaCommonWorkerCommand) context.getBean("commonWorkerCommand");
        taskExecutor.execute(commonWorkerCommand);
    }*/

    //ETC...

/*    @Bean
    @ConditionalOnExpression("${module.common:kafka}")
    public void specificWorkerCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        KafkaSpecificWorkerCommand specificWorkerCommand = (KafkaSpecificWorkerCommand) context.getBean("specificWorkerCommand");
        taskExecutor.execute(specificWorkerCommand);
    }*/

    //ETC...
}
