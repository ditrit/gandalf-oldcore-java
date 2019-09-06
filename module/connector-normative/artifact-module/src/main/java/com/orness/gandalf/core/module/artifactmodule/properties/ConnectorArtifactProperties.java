package com.orness.gandalf.core.module.artifactmodule.properties;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import org.springframework.beans.factory.annotation.Value;

public class ConnectorArtifactProperties extends ConnectorProperties {

    @Value("${${instance.name}.${connector.type}.${connector.name}.endPointName}")
    private String endPointName;
    @Value("${${instance.name}.${connector.type}.${connector.name}.endPointConnection}")
    private String endPointConnection;

    public String getEndPointName() {
        return endPointName;
    }

    public void setEndPointName(String endPointName) {
        this.endPointName = endPointName;
    }

    public String getEndPointConnection() {
        return endPointConnection;
    }

    public void setEndPointConnection(String endPointConnection) {
        this.endPointConnection = endPointConnection;
    }



}
