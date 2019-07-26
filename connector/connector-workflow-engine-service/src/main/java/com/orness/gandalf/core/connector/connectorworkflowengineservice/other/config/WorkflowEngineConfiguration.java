package com.orness.gandalf.core.connector.connectorworkflowengineservice.other.config;

import com.orness.gandalf.core.module.zeebemodule.common.worker.ZeebeCommonWorkerCommand;
import com.orness.gandalf.core.module.zeebemodule.specific.worker.ZeebeSpecificWorkerCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class WorkflowEngineConfiguration {

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

    //ZEEBE
    @Bean
    @ConditionalOnExpression("${module.common:kafka}")
    public void commonWorkerCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        ZeebeCommonWorkerCommand commonWorkerCommand = (ZeebeCommonWorkerCommand) context.getBean("commonWorkerCommand");
        taskExecutor.execute(commonWorkerCommand);
    }

    @Bean
    @ConditionalOnExpression("${module.common:kafka}")
    public void specificWorkerCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        ZeebeSpecificWorkerCommand specificWorkerCommand = (ZeebeSpecificWorkerCommand) context.getBean("specificWorkerCommand");
        taskExecutor.execute(specificWorkerCommand);
    }

    //ETC...
}
