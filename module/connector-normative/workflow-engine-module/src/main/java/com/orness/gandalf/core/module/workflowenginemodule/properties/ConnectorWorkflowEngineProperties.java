package com.orness.gandalf.core.module.workflowenginemodule.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ConnectorWorkflowEngineProperties {

    @Value("${connector.name}")
    private String connectorName;
    @Value("${${connector.name}.workflowengineConnection}")
    private String workflowengineConnection;

    public String getWorkflowengineConnection() {
        return workflowengineConnection;
    }

    public void setWorkflowengineConnection(String workflowengineConnection) {
        this.workflowengineConnection = workflowengineConnection;
    }
}
