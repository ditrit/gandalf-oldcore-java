package com.orness.gandalf.core.module.busmodule.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ConnectorBusProperties {

    @Value("${${instance.name}.${connector.type}.${connector.name}.busEndPointConnection}")
    private String busEndPointConnection;

    public String getBusEndPointConnection() {
        return busEndPointConnection;
    }

    public void setBusEndPointConnection(String busEndPointConnection) {
        this.busEndPointConnection = busEndPointConnection;
    }
}
