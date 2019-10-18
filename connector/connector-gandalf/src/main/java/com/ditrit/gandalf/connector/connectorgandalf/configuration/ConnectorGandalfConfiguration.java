package com.ditrit.gandalf.connector.connectorgandalf.configuration;

import com.ditrit.gandalf.core.connectorcore.service.ConnectorClientService;
import com.ditrit.gandalf.core.connectorcore.connector.ConnectorEvent;
import com.ditrit.gandalf.core.connectorcore.connector.ConnectorCommand;
import com.ditrit.gandalf.core.connectorcore.service.ConnectorListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private ApplicationContext context;
    @Value("${${instance.name}.connectors.${connector.type}.${connector.name}.target.type}")
    private String targetType;

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
            this.taskExecutor().execute(gandalfConnectorCommand);
        }
    }

    @Bean
    public void gandalfConnectorEvent() {
        ConnectorEvent gandalfConnectorEvent = (ConnectorEvent) context.getBean("connectorEvent");
        if(gandalfConnectorEvent != null) {
            this.taskExecutor().execute(gandalfConnectorEvent);
        }
    }

    @Bean
    public void gandalfConnectorClientService() {
        ConnectorClientService gandalfConnectorClientService = (ConnectorClientService) context.getBean("connectorClientService");
        if(gandalfConnectorClientService != null) {
            this.taskExecutor().execute(gandalfConnectorClientService);
        }
    }

    @Bean
    public void gandalfConnectorListenerService() {
        ConnectorListenerService gandalfConnectorListenerService = (ConnectorListenerService) context.getBean("connectorListenerService");
        if(gandalfConnectorListenerService != null) {
            this.taskExecutor().execute(gandalfConnectorListenerService);
        }
    }
}
