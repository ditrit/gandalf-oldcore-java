package com.orness.gandalf.core.connector.connectorworkflowengineservice.specific.zeebe.config;

import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectorWorkflowEngineBrokerConfig {

    @Value("${gandalf.workflowengine.broker}")
    private String brokerAddress;

    @Bean
    public ZeebeClient zeebe() {
        //Client
        ZeebeClient zeebeClient = ZeebeClient.newClientBuilder()
                .brokerContactPoint(brokerAddress)
                .build();
        return zeebeClient;
    }
}
