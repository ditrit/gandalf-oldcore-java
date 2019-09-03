package com.orness.gandalf.core.module.databasemodule.properties;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import org.springframework.beans.factory.annotation.Value;

public class ConnectorDatabaseProperties extends ConnectorProperties {

    @Value("${${instance.name}.${connector.type}.${connector.name}.databaseEndPointConnection}")
    private String databaseEndPointConnection;

    public String getDatabaseEndPointConnection() {
        return databaseEndPointConnection;
    }

    public void setDatabaseEndPointConnection(String databaseEndPointConnection) {
        this.databaseEndPointConnection = databaseEndPointConnection;
    }
}
