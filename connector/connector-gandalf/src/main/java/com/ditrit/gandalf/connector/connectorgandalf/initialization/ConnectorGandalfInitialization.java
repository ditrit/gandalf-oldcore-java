package com.ditrit.gandalf.connector.connectorgandalf.initialization;

import com.ditrit.gandalf.core.connectorcore.properties.ConnectorProperties;
import com.ditrit.gandalf.core.connectorcore.service.ConnectorClientService;
import com.ditrit.gandalf.core.connectorcore.service.ConnectorListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.zeromq.ZMsg;

@Configuration
@ComponentScan(basePackages = {"com.ditrit.gandalf.core.connectorcore"})
public class ConnectorGandalfInitialization {

    private ApplicationContext context;
    private ConnectorProperties connectorProperties;

    @Autowired
    public ConnectorGandalfInitialization(ApplicationContext context, ConnectorProperties connectorProperties) {
        this.context = context;
        this.connectorProperties = connectorProperties;
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
    public void gandalfConnectorClientService() {
        ConnectorClientService gandalfConnectorClientService = (ConnectorClientService) context.getBean("connectorClientService");
        if(gandalfConnectorClientService != null) {
            this.taskExecutor().execute(gandalfConnectorClientService);
            this.initialize(gandalfConnectorClientService);
        }
    }

    @Bean
    public void gandalfConnectorListenerService() {
        ConnectorListenerService gandalfConnectorListenerService = (ConnectorListenerService) context.getBean("connectorListenerService");
        if(gandalfConnectorListenerService != null) {
            this.taskExecutor().execute(gandalfConnectorListenerService);
        }
    }

    private void initialize(ConnectorClientService connectorClientService) {
        ZMsg response = connectorClientService.sendRequest("configuration");
        Object[] responseConnections =  response.toArray();

        this.connectorProperties.setConnectorCommandFrontEndReceiveConnection(responseConnections[0].toString());
        this.connectorProperties.setConnectorCommandFrontEndSendConnection(responseConnections[1].toString());
        this.connectorProperties.setConnectorEventFrontEndReceiveConnection(responseConnections[2].toString());
        this.connectorProperties.setConnectorEventFrontEndSendConnection(responseConnections[3].toString());
    }
}
