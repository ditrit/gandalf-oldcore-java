package com.orness.gandalf.core.library.gandalfjavaclient.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
public class GandalfClientProperties {

    @Value("${connector.name}")
    private String connectorName;
    @Value("${${connector.name}.publisherConnection}")
    private String publisherConnection;
    @Value("${${connector.name}.clientConnections}")
    private List<String> clientConnections;

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public String getPublisherConnection() {
        return publisherConnection;
    }

    public void setPublisherConnection(String publisherConnection) {
        this.publisherConnection = publisherConnection;
    }

    public List<String> getClientConnections() {
        return clientConnections;
    }

    public void setClientConnections(List<String> clientConnections) {
        this.clientConnections = clientConnections;
    }
}
