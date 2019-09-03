package com.orness.gandalf.core.module.artifactmodule.properties;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import org.springframework.beans.factory.annotation.Value;

//TODO REVOIR
public class ConnectorArtifactProperties extends ConnectorProperties {

    @Value("${${instance.name}.${connector.type}.${connector.name}.feignName}")
    private String feignName;
    @Value("${${instance.name}.${connector.type}.${connector.name}.artifactEndPointConnection}")
    private String artifactEndPointConnection;

    public String getFeignName() {
        return feignName;
    }

    public void setFeignName(String feignName) {
        this.feignName = feignName;
    }

    public String getArtifactEndPointConnection() {
        return artifactEndPointConnection;
    }

    public void setArtifactEndPointConnection(String artifactEndPointConnection) {
        this.artifactEndPointConnection = artifactEndPointConnection;
    }
}
