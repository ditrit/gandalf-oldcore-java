package com.orness.gandalf.core.module.gandalfmodule.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ConnectorGandalfProperties {


    @Value("${${instance.name}.${connector.type}.${connector.name}.connectorCommandBackEndConnection}")
    private String connectorCommandBackEndConnection;
    @Value("${${instance.name}.${connector.type}.${connector.name}.connectorEventBackEndConnection}")
    private String connectorEventBackEndConnection;
    @Value("${${connector.name}.${connector.type}.${connector.name}.topics}")
    private List<String> topics;

    public String getConnectorCommandBackEndConnection() {
        return connectorCommandBackEndConnection;
    }

    public void setConnectorCommandBackEndConnection(String connectorCommandBackEndConnection) {
        this.connectorCommandBackEndConnection = connectorCommandBackEndConnection;
    }

    public String getConnectorEventBackEndConnection() {
        return connectorEventBackEndConnection;
    }

    public void setConnectorEventBackEndConnection(String connectorEventBackEndConnection) {
        this.connectorEventBackEndConnection = connectorEventBackEndConnection;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
