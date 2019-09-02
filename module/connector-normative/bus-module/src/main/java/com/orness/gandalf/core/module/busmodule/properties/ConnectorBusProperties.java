package com.orness.gandalf.core.module.busmodule.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ConnectorBusProperties {

    @Value("${connector.name}")
    private String connectorName;
    @Value("${${connector.name}.busConnection}")
    private String busConnection;

    public String getBusConnection() {
        return busConnection;
    }

    public void setBusConnection(String busConnection) {
        this.busConnection = busConnection;
    }
}
