package com.ditrit.gandalf.modules.abstractconnectors.abstractartifact.properties;

import com.ditrit.gandalf.gandalfjava.core.connectorcore.properties.ConnectorProperties;
import org.springframework.beans.factory.annotation.Value;

public class ConnectorArtifactProperties extends ConnectorProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.";

    @Value("${" + PROPERTIES_BASE + "type}")
    private String type;
    @Value("${" + PROPERTIES_BASE + "endpoint.name}")
    private String endPointName;
    @Value("${" + PROPERTIES_BASE + "endpoint.uri}")
    private String endPointConnection;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
