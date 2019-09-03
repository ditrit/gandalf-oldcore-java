package com.orness.gandalf.core.module.customorchestratormodule.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
@Profile(value = "custom-orchestrator")
public class ConnectorCustomOrchestratorProperties {

    @Value("${${connector.name}.${connector.type}.${connector.name}.connectorBackEndConnection}")
    private String connectorBackEndConnection;
    @Value("${${connector.name}.${connector.type}.${connector.name}.topics}")
    private List<String> topics;

    public String getConnectorBackEndConnection() {
        return connectorBackEndConnection;
    }

    public void setConnectorBackEndConnection(String connectorBackEndConnection) {
        this.connectorBackEndConnection = connectorBackEndConnection;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
