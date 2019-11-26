package com.ditrit.gandalf.gandalfjava.functions.functionszeebe.core.config;

import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ditrit.gandalf.gandalfjava.functions.functionszeebe.properties.ConnectorZeebeProperties;

@Configuration
@ConditionalOnBean(ConnectorZeebeProperties.class)
public class ConnectorZeebeConfiguration {

    private ConnectorZeebeProperties connectorZeebeProperties;

    @Autowired
    public ConnectorZeebeConfiguration(ConnectorZeebeProperties connectorZeebeProperties) {
        this.connectorZeebeProperties = connectorZeebeProperties;
    }

    @Bean
    public ZeebeClient zeebe() {
        //TODO
        System.out.println("Connector connect to endpoint: " /*+ this.connectorZeebeProperties.getEndPointConnection()*/);
        ZeebeClient zeebeClient = ZeebeClient.newClientBuilder()
                .brokerContactPoint(/*this.connectorZeebeProperties.getEndPointConnection()*/)
                .build();
        return zeebeClient;
    }
}
