package com.orness.gandalf.core.module.orchestratormodule.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ConnectorOrchestratorProperties {

    @Value("${${instance.name}.${connector.type}.${connector.name}.orchestratorEndPointConnection}")
    private String orchestratorEndPointConnection;

    public String getOrchestratorEndPointConnection() {
        return orchestratorEndPointConnection;
    }

    public void setOrchestratorEndPointConnection(String orchestratorEndPointConnection) {
        this.orchestratorEndPointConnection = orchestratorEndPointConnection;
    }
}
