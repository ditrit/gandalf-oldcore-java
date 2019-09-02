package com.orness.gandalf.core.module.connectorcore.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
public class ConnectorProperties {

    @Value("${connector.name}")
    private String connectorName;
    @Value("${${connector.name}.routingWorkerFrontEndConnections}")
    private List<String> routingWorkerFrontEndConnections;
    @Value("${${connector.name}.routingWorkerBackEndConnection}")
    private String routingWorkerBackEndConnection;
    @Value("${${connector.name}.routingSubscriberFrontEndConnection}")
    private String routingSubscriberFrontEndConnection;
    @Value("${${connector.name}.routingSubscriberBackEndConnection}")
    private String routingSubscriberBackEndConnection;

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public List<String> getRoutingWorkerFrontEndConnections() {
        return routingWorkerFrontEndConnections;
    }

    public void setRoutingWorkerFrontEndConnections(List<String> routingWorkerFrontEndConnections) {
        this.routingWorkerFrontEndConnections = routingWorkerFrontEndConnections;
    }

    public String getRoutingWorkerBackEndConnection() {
        return routingWorkerBackEndConnection;
    }

    public void setRoutingWorkerBackEndConnection(String routingWorkerBackEndConnection) {
        this.routingWorkerBackEndConnection = routingWorkerBackEndConnection;
    }

    public String getRoutingSubscriberFrontEndConnection() {
        return routingSubscriberFrontEndConnection;
    }

    public void setRoutingSubscriberFrontEndConnection(String routingSubscriberFrontEndConnection) {
        this.routingSubscriberFrontEndConnection = routingSubscriberFrontEndConnection;
    }

    public String getRoutingSubscriberBackEndConnection() {
        return routingSubscriberBackEndConnection;
    }

    public void setRoutingSubscriberBackEndConnection(String routingSubscriberBackEndConnection) {
        this.routingSubscriberBackEndConnection = routingSubscriberBackEndConnection;
    }
}
