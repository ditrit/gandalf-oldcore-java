package com.orness.gandalf.core.connector.connectorworkflowengineservice.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectorWorkflowEngineCommonConfiguration {

    @Autowired
    private ApplicationContext context;

/*    @Bean
    public void commonWorkerCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        RunnableWorkerZeroMQ commonWorkerCommand;
        //TODO PROPERTIES
        String type ="";
        switch(type) {
            *//*case "RABBITMQ":
                commonWorkerCommand = (KafkaCommonWorkerCommand) context.getBean("commonWorkerCommand");
                break;*//*
            default:
                commonWorkerCommand = (KafkaCommonWorkerCommand) context.getBean("commonWorkerCommand");
                break;
        }
        taskExecutor.execute(commonWorkerCommand);
    }*/
}
