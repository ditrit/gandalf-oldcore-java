package com.orness.gandalf.core.connector.connectorworkflowengineservice.common;

import com.orness.gandalf.core.module.zeebemodule.common.worker.ZeebeCommonWorkerCommand;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ConnectorWorkflowEngineCommonConfiguration {

    @Autowired
    private ApplicationContext context;

    @Bean
    public void commonWorkerCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        RunnableWorkerZeroMQ commonWorkerCommand;
        String type ="";
        switch(type) {
            /*case "CAMUNDA":
                commonWorkerCommand = (KafkaCommonWorkerCommand) context.getBean("commonWorkerCommand");
                break;*/
            default: //ZEEBE
                commonWorkerCommand = (ZeebeCommonWorkerCommand) context.getBean("commonWorkerCommand");
                break;
        }
        taskExecutor.execute(commonWorkerCommand);
    }
}
