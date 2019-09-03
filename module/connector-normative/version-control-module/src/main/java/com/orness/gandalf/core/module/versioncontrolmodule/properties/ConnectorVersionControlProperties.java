package com.orness.gandalf.core.module.versioncontrolmodule.properties;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import org.springframework.beans.factory.annotation.Value;

public class ConnectorVersionControlProperties extends ConnectorProperties {

    @Value("${${instance.name}.${connector.type}.${connector.name}.versionControlEndPointConnection}")
    private String versionControlEndPointConnection;

    public String getVersionControlEndPointConnection() {
        return versionControlEndPointConnection;
    }

    public void setVersionControlEndPointConnection(String versionControlEndPointConnection) {
        this.versionControlEndPointConnection = versionControlEndPointConnection;
    }
}
