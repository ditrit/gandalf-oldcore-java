package com.ditrit.gandalf.core.clientcore.worker.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkerClientProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.";

    @Value("${connector.name}")
    private String connectorName;

    @Value("${" + PROPERTIES_BASE + "connectorCommandBackEndConnection:tcp://127.0.0.1:9020}")
    private String connectorCommandBackEndConnection;
    @Value("${" + PROPERTIES_BASE + "connectorEventBackEndSendConnection:tcp://127.0.0.1:9022}")
    private String connectorEventBackEndSendConnection;

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public String getConnectorCommandBackEndConnection() {
        return connectorCommandBackEndConnection;
    }

    public void setConnectorCommandBackEndConnection(String connectorCommandBackEndConnection) {
        this.connectorCommandBackEndConnection = connectorCommandBackEndConnection;
    }

    public String getConnectorEventBackEndSendConnection() {
        return connectorEventBackEndSendConnection;
    }

    public void setConnectorEventBackEndSendConnection(String connectorEventBackEndSendConnection) {
        this.connectorEventBackEndSendConnection = connectorEventBackEndSendConnection;
    }
}
