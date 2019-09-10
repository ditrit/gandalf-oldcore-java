package com.orness.gandalf.core.module.workflowenginemodule.properties;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import org.springframework.beans.factory.annotation.Value;

public class ConnectorWorkflowEngineProperties extends ConnectorProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.";

    @Value("${" + PROPERTIES_BASE + "endpoint.uri}")
    private String endPointConnection;

    public String getEndPointConnection() {
        return endPointConnection;
    }

    public void setEndPointConnection(String endPointConnection) {
        this.endPointConnection = endPointConnection;
    }
}
