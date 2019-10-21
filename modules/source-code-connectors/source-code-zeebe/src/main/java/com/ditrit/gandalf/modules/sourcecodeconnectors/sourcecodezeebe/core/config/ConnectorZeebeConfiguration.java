package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.core.config;

import com.ditrit.gandalf.modules.abstractconnectors.abstractworkflowengine.properties.ConnectorWorkflowEngineProperties;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.properties.ConnectorZeebeProperties;
import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnBean(ConnectorZeebeProperties.class)
public class ConnectorZeebeConfiguration {

    private ConnectorWorkflowEngineProperties workflowEngineProperties;

    @Autowired
    public ConnectorZeebeConfiguration(ConnectorWorkflowEngineProperties workflowEngineProperties) {
        this.workflowEngineProperties = workflowEngineProperties;
    }

    @Bean
    public ZeebeClient zeebe() {
        System.out.println("Connector connect to endpoint: " + this.workflowEngineProperties.getEndPointConnection());
        ZeebeClient zeebeClient = ZeebeClient.newClientBuilder()
                .brokerContactPoint(this.workflowEngineProperties.getEndPointConnection())
                .build();
        return zeebeClient;
    }
}
