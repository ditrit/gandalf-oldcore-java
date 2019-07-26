package com.orness.gandalf.core.connector.connectorbusservice.gandalf;

import com.orness.gandalf.core.module.gandalfmodule.communication.command.GandalfWorkerCommand;
import com.orness.gandalf.core.module.kafkamodule.common.worker.KafkaCommonWorkerCommand;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ConnectorBusGandalfConfiguration {

    @Autowired
    private ApplicationContext context;

    @Bean
    public void gandalfWorkerCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        RunnableWorkerZeroMQ gandalfWorkerCommand = (GandalfWorkerCommand) context.getBean("gandalfWorkerCommand");
        taskExecutor.execute(gandalfWorkerCommand);
    }
}
