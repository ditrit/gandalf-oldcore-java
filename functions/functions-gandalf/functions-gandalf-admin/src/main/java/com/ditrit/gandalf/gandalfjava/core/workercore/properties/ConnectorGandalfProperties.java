package com.ditrit.gandalf.gandalfjava.core.workercore.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ConnectorGandalfProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.";

    @Value("${instance.name}")
    private String instanceName;
    @Value("${connector.name}")
    private String connectorName;
    //@Value("${" + PROPERTIES_BASE + "topics}")
    private List<String> topics;

    @Value("${" + PROPERTIES_BASE + "connectorCommandBackEndSendConnection:tcp://*:9000}")
    private String connectorCommandBackEndSendConnection;
    @Value("${" + PROPERTIES_BASE + "connectorCommandBackEndReceiveConnection:tcp://*:9001}")
    private String connectorCommandBackEndReceiveConnection;
    @Value("${" + PROPERTIES_BASE + "connectorEventBackEndSendConnection:tcp://*:9010}")
    private String connectorEventBackEndSendConnection;
    @Value("${" + PROPERTIES_BASE + "connectorEventBackEndReceiveConnection:tcp://*:9011}")
    private String connectorEventBackEndReceiveConnection;

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public String getConnectorCommandBackEndSendConnection() {
        return connectorCommandBackEndSendConnection;
    }

    public void setConnectorCommandBackEndSendConnection(String connectorCommandBackEndSendConnection) {
        this.connectorCommandBackEndSendConnection = connectorCommandBackEndSendConnection;
    }

    public String getConnectorCommandBackEndReceiveConnection() {
        return connectorCommandBackEndReceiveConnection;
    }

    public void setConnectorCommandBackEndReceiveConnection(String connectorCommandBackEndReceiveConnection) {
        this.connectorCommandBackEndReceiveConnection = connectorCommandBackEndReceiveConnection;
    }

    public String getConnectorEventBackEndSendConnection() {
        return connectorEventBackEndSendConnection;
    }

    public void setConnectorEventBackEndSendConnection(String connectorEventBackEndSendConnection) {
        this.connectorEventBackEndSendConnection = connectorEventBackEndSendConnection;
    }

    public String getConnectorEventBackEndReceiveConnection() {
        return connectorEventBackEndReceiveConnection;
    }

    public void setConnectorEventBackEndReceiveConnection(String connectorEventBackEndReceiveConnection) {
        this.connectorEventBackEndReceiveConnection = connectorEventBackEndReceiveConnection;
    }
}
