package com.orness.gandalf.core.module.connectororchestratorservice.specific;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectorOrchestratorSpecificConfiguration {

    @Autowired
    private ApplicationContext context;

/*    @Bean
    public void specificWorkerCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        RunnableWorkerZeroMQ specificWorkerCommand;
        //TODO PROPERTIES
        String type ="";
        switch(type) {
            *//*case "RABBITMQ":
                commonWorkerCommand = (KafkaCommonWorkerCommand) context.getBean("commonWorkerCommand");
                break;*//*
            default:
                specificWorkerCommand = (KafkaSpecificWorkerCommand) context.getBean("specificWorkerCommand");
                break;
        }
        taskExecutor.execute(specificWorkerCommand);
    }*/
}
