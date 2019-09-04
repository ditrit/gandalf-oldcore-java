package com.orness.gandalf.core.module.workflowenginemodule.properties;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import org.springframework.beans.factory.annotation.Value;

public class ConnectorWorkflowEngineProperties extends ConnectorProperties {

    @Value("${${instance.name}.${connector.type}.${connector.name}.endPointConnection}")
    private String endPointConnection;

    public String getEndPointConnection() {
        return endPointConnection;
    }

    public void setEndPointConnection(String endPointConnection) {
        this.endPointConnection = endPointConnection;
    }
}
