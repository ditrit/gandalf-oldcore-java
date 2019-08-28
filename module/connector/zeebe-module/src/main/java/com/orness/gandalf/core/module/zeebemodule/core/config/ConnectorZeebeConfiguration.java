package com.orness.gandalf.core.module.zeebemodule.core.config;

import com.orness.gandalf.core.module.workflowenginemodule.properties.ConnectorWorkflowEngineProperties;
import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = "zeebe-module")
public class ConnectorZeebeConfiguration {

    private ConnectorWorkflowEngineProperties workflowEngineProperties;

    @Autowired
    public ConnectorZeebeConfiguration(ConnectorWorkflowEngineProperties workflowEngineProperties) {
        this.workflowEngineProperties = workflowEngineProperties;
    }

    @Bean
    public ZeebeClient zeebe() {
        System.out.println(workflowEngineProperties.getBroker());
        ZeebeClient zeebeClient = ZeebeClient.newClientBuilder()
                .brokerContactPoint(this.workflowEngineProperties.getBroker())
                .build();
        return zeebeClient;
    }
}
