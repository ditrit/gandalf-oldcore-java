package com.orness.gandalf.core.connector.connectorbusservice.config;

import com.orness.gandalf.core.module.kafkamodule.common.worker.KafkaCommonWorkerCommand;
import com.orness.gandalf.core.module.kafkamodule.specific.worker.KafkaSpecificWorkerCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//TODO
@Configuration
public class ConnectorBusConfiguration {

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

    //KAFKA
    @Bean
    @ConditionalOnExpression("${module.common:kafka}")
    public void commonWorkerCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        KafkaCommonWorkerCommand commonWorkerCommand = (KafkaCommonWorkerCommand) context.getBean("commonWorkerCommand");
        taskExecutor.execute(commonWorkerCommand);
    }

    @Bean
    @ConditionalOnExpression("${module.common:kafka}")
    public void specificWorkerCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        KafkaSpecificWorkerCommand specificWorkerCommand = (KafkaSpecificWorkerCommand) context.getBean("specificWorkerCommand");
        taskExecutor.execute(specificWorkerCommand);
    }

    //ETC...
}
