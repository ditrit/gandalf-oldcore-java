package com.ditrit.gandalf.core.clientcore.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.";

    @Value("${connector.name}")
    private String connectorName;

    @Value("${" + PROPERTIES_BASE + "connectorCommandBackEndSendConnection:tcp://*:9000}")
    private String connectorCommandBackEndSendConnection;
    @Value("${" + PROPERTIES_BASE + "connectorEventBackEndSendConnection:tcp://*:9010}")
    private String connectorEventBackEndSendConnection;

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public String getConnectorCommandBackEndSendConnection() {
        return connectorCommandBackEndSendConnection;
    }

    public void setConnectorCommandBackEndSendConnection(String connectorCommandBackEndSendConnection) {
        this.connectorCommandBackEndSendConnection = connectorCommandBackEndSendConnection;
    }

    public String getConnectorEventBackEndSendConnection() {
        return connectorEventBackEndSendConnection;
    }

    public void setConnectorEventBackEndSendConnection(String connectorEventBackEndSendConnection) {
        this.connectorEventBackEndSendConnection = connectorEventBackEndSendConnection;
    }
}
