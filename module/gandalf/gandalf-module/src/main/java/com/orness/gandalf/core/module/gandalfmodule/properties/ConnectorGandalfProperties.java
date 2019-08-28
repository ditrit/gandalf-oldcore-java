package com.orness.gandalf.core.module.gandalfmodule.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="connector.gandalf")
public class ConnectorGandalfProperties {


    private String routingWorkerConnection;
    private String routingSubscriberConnection;
    private List<String> topics;

    public String getRoutingWorkerConnection() {
        return routingWorkerConnection;
    }

    public void setRoutingWorkerConnection(String routingWorkerConnection) {
        this.routingWorkerConnection = routingWorkerConnection;
    }

    public String getRoutingSubscriberConnection() {
        return routingSubscriberConnection;
    }

    public void setRoutingSubscriberConnection(String routingSubscriberConnection) {
        this.routingSubscriberConnection = routingSubscriberConnection;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
