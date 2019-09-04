package com.orness.gandalf.core.module.artifactmodule.properties;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import org.springframework.beans.factory.annotation.Value;

public class ConnectorArtifactProperties extends ConnectorProperties {

    @Value("${${instance.name}.${connector.type}.${connector.name}.feignName}")
    private String feignName;
    @Value("${${instance.name}.${connector.type}.${connector.name}.endPointConnection}")
    private String endPointConnection;

    public String getFeignName() {
        return feignName;
    }

    public void setFeignName(String feignName) {
        this.feignName = feignName;
    }

    public String getEndPointConnection() {
        return endPointConnection;
    }

    public void setEndPointConnection(String endPointConnection) {
        this.endPointConnection = endPointConnection;
    }



}
