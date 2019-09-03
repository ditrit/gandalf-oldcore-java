package com.orness.gandalf.core.module.busmodule.properties;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import org.springframework.beans.factory.annotation.Value;

public class ConnectorBusProperties extends ConnectorProperties {

    @Value("${${instance.name}.${connector.type}.${connector.name}.busEndPointConnection}")
    private String busEndPointConnection;

    public String getBusEndPointConnection() {
        return busEndPointConnection;
    }

    public void setBusEndPointConnection(String busEndPointConnection) {
        this.busEndPointConnection = busEndPointConnection;
    }
}
