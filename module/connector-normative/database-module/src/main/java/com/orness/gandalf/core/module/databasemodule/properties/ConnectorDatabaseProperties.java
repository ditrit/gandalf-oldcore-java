package com.orness.gandalf.core.module.databasemodule.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ConnectorDatabaseProperties {

    @Value("${connector.name}")
    private String connectorName;
    @Value("${${connector.name}.databaseConnection}")
    private String databaseConnection;

    public String getDatabaseConnection() {
        return databaseConnection;
    }

    public void setDatabaseConnection(String databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
}
