package com.orness.gandalf.core.module.busmodule.properties;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import org.springframework.beans.factory.annotation.Value;

public class ConnectorBusProperties extends ConnectorProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.";

    @Value("${" + PROPERTIES_BASE + "type}")
    private String type;

    @Value("${" + PROPERTIES_BASE + "endpoint.uri}")
    private String endPointConnection;

    public void setType(String type) {
        this.type = type;
    }

    public String getEndPointConnection() {
        return endPointConnection;
    }

    public void setEndPointConnection(String endPointConnection) {
        this.endPointConnection = endPointConnection;
    }
}
