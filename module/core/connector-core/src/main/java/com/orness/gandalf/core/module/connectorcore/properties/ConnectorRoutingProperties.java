package com.orness.gandalf.core.module.connectorcore.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="gandalf.routing")
public class ConnectorRoutingProperties {

    private List<String> routingWorkerFrontEndConnections;
    private String routingWorkerBackEndConnection;
    private String routingSubscriberFrontEndConnection;
    private String routingSubscriberBackEndConnection;

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
