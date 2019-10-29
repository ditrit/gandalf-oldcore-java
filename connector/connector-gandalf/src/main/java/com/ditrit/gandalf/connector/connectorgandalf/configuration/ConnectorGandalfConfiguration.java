package com.ditrit.gandalf.connector.connectorgandalf.configuration;

import com.ditrit.gandalf.core.connectorcore.connector.ConnectorEvent;
import com.ditrit.gandalf.core.connectorcore.connector.ConnectorCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan(basePackages = {"com.ditrit.gandalf.core.connectorcore"})
@Order
public class ConnectorGandalfConfiguration {

    private ApplicationContext context;
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    public ConnectorGandalfConfiguration(ApplicationContext context, ThreadPoolTaskExecutor taskExecutor) {
        this.context = context;
        this.taskExecutor = taskExecutor;
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(10);
        pool.setMaxPoolSize(20);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Bean
    public void gandalfConnectorCommand() {
        ConnectorCommand gandalfConnectorCommand = (ConnectorCommand) context.getBean("connectorCommand");
        if(gandalfConnectorCommand != null) {
            this.taskExecutor.execute(gandalfConnectorCommand);
        }
    }

    @Bean
    public void gandalfConnectorEvent() {
        ConnectorEvent gandalfConnectorEvent = (ConnectorEvent) context.getBean("connectorEvent");
        if(gandalfConnectorEvent != null) {
            this.taskExecutor.execute(gandalfConnectorEvent);
        }
    }
}
