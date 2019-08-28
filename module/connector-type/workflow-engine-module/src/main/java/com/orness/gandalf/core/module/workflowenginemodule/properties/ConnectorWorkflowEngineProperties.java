package com.orness.gandalf.core.module.workflowenginemodule.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="connector.workflowengine")
public class ConnectorWorkflowEngineProperties {

    private String workflowengineConnection;

    public String getWorkflowengineConnection() {
        return workflowengineConnection;
    }

    public void setWorkflowengineConnection(String workflowengineConnection) {
        this.workflowengineConnection = workflowengineConnection;
    }
}
