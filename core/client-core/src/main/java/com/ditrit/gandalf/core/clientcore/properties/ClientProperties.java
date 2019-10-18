package com.ditrit.gandalf.core.clientcore.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientProperties {


    @Value("${connector.name}")
    private String connectorName;

    private String connectorCommandBackEndSendConnection;
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
