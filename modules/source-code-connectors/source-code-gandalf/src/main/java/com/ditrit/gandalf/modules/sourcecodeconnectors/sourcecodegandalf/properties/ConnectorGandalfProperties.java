package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ConnectorGandalfProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.";

    @Value("${" + PROPERTIES_BASE + "connectorCommandBackEndConnection:tcp://127.0.0.1:9020}")
    private String connectorCommandBackEndConnection;
    @Value("${" + PROPERTIES_BASE + "connectorEventBackEndConnection:tcp://127.0.0.1:9021}")
    private String connectorEventBackEndConnection;
    //@Value("${" + PROPERTIES_BASE + "topics}")
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
