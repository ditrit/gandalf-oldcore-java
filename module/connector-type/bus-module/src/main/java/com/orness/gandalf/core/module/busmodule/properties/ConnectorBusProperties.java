package com.orness.gandalf.core.module.busmodule.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="connector.bus")
public class ConnectorBusProperties {

    private String busConnection;

    public String getBusConnection() {
        return busConnection;
    }

    public void setBusConnection(String busConnection) {
        this.busConnection = busConnection;
    }
}
