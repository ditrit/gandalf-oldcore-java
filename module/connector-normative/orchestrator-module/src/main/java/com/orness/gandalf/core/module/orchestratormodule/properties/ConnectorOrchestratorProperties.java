package com.orness.gandalf.core.module.orchestratormodule.properties;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import org.springframework.beans.factory.annotation.Value;

public class ConnectorOrchestratorProperties extends ConnectorProperties {

    @Value("${${instance.name}.${connector.type}.${connector.name}.orchestratorEndPointConnection}")
    private String orchestratorEndPointConnection;

    public String getOrchestratorEndPointConnection() {
        return orchestratorEndPointConnection;
    }

    public void setOrchestratorEndPointConnection(String orchestratorEndPointConnection) {
        this.orchestratorEndPointConnection = orchestratorEndPointConnection;
    }
}
