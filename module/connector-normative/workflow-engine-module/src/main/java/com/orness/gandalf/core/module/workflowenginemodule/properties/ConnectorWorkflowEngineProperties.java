package com.orness.gandalf.core.module.workflowenginemodule.properties;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import org.springframework.beans.factory.annotation.Value;

public class ConnectorWorkflowEngineProperties extends ConnectorProperties {

    @Value("${${instance.name}.${connector.type}.${connector.name}.endPointConnection}")
    private String workflowEngineEndPointConnection;

    public String getWorkflowEngineEndPointConnection() {
        return workflowEngineEndPointConnection;
    }

    public void setWorkflowEngineEndPointConnection(String workflowEngineEndPointConnection) {
        this.workflowEngineEndPointConnection = workflowEngineEndPointConnection;
    }
}
