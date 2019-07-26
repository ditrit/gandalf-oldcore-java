package com.orness.gandalf.core.connector.connectorbusservice.specific;

import com.orness.gandalf.core.module.kafkamodule.common.worker.KafkaCommonWorkerCommand;
import com.orness.gandalf.core.module.kafkamodule.specific.worker.KafkaSpecificWorkerCommand;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ConnectorBusSpecificConfiguration {

    @Autowired
    private ApplicationContext context;

    @Bean
    public void specificWorkerCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        RunnableWorkerZeroMQ specificWorkerCommand;
        //TODO PROPERTIES
        String type ="";
        switch(type) {
            /*case "RABBITMQ":
                commonWorkerCommand = (KafkaCommonWorkerCommand) context.getBean("commonWorkerCommand");
                break;*/
            default:
                specificWorkerCommand = (KafkaSpecificWorkerCommand) context.getBean("specificWorkerCommand");
                break;
        }
        taskExecutor.execute(specificWorkerCommand);
    }
}
