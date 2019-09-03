package com.orness.gandalf.core.module.versioncontrolmodule.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ConnectorVersionControlProperties {

    @Value("${${instance.name}.${connector.type}.${connector.name}.connectorVersionControlEndPointConnection}")
    private String connectorVersionControlEndPointConnection;

    public String getConnectorVersionControlEndPointConnection() {
        return connectorVersionControlEndPointConnection;
    }

    public void setConnectorVersionControlEndPointConnection(String connectorVersionControlEndPointConnection) {
        this.connectorVersionControlEndPointConnection = connectorVersionControlEndPointConnection;
    }
}
