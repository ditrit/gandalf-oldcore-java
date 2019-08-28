package com.orness.gandalf.core.library.gandalfjavaclient.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="gandalf.client")
public class GandalfClientProperties {

    private String name;
    private String publisherConnection;
    private List<String> clientConnections;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
