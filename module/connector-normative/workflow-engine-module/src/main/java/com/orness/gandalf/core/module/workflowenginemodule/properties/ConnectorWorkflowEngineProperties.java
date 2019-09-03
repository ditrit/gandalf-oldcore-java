package com.orness.gandalf.core.module.workflowenginemodule.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ConnectorWorkflowEngineProperties {

    @Value("${${instance.name}.${connector.type}.${connector.name}.workflowEngineEndPointConnection}")
    private String workflowEngineEndPointConnection;

    public String getWorkflowEngineEndPointConnection() {
        return workflowEngineEndPointConnection;
    }

    public void setWorkflowEngineEndPointConnection(String workflowEngineEndPointConnection) {
        this.workflowEngineEndPointConnection = workflowEngineEndPointConnection;
    }
}
