package com.orness.gandalf.core.connector.connectorartifactservice.common;

import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ConnectorArtifactCommonConfiguration {

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
