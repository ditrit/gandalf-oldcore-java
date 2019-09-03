package com.orness.gandalf.core.module.databasemodule.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ConnectorDatabaseProperties {

    @Value("${${instance.name}.${connector.type}.${connector.name}.databaseEndPointConnection}")
    private String databaseEndPointConnection;

    public String getDatabaseEndPointConnection() {
        return databaseEndPointConnection;
    }

    public void setDatabaseEndPointConnection(String databaseEndPointConnection) {
        this.databaseEndPointConnection = databaseEndPointConnection;
    }
}
