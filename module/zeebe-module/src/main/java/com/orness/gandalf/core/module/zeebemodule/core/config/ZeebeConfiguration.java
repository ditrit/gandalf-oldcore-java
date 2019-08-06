package com.orness.gandalf.core.module.zeebemodule.core.config;

import com.orness.gandalf.core.module.workflowenginemodule.core.properties.WorkflowEngineProperties;
import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = "zeebe-module")
public class ZeebeConfiguration {

    private WorkflowEngineProperties workflowEngineProperties;

    @Autowired
    public ZeebeConfiguration(WorkflowEngineProperties workflowEngineProperties) {
        this.workflowEngineProperties = workflowEngineProperties;
    }

    @Bean
    public ZeebeClient zeebe() {
        ZeebeClient zeebeClient = ZeebeClient.newClientBuilder()
                .brokerContactPoint(this.workflowEngineProperties.getBroker())
                .build();
        return zeebeClient;
    }
}
