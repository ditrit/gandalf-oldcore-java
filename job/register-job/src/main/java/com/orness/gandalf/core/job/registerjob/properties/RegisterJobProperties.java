package com.orness.gandalf.core.job.registerjob.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegisterJobProperties {

    @Value("${job.endPointConnection}")
    private String endPointConnection;
    @Value("${job.connector.endPointName}")
    private String connectorEndPointName;

    public String getEndPointConnection() {
        return endPointConnection;
    }

    public void setEndPointConnection(String endPointConnection) {
        this.endPointConnection = endPointConnection;
    }

    public String getConnectorEndPointName() {
        return connectorEndPointName;
    }

    public void setConnectorEndPointName(String connectorEndPointName) {
        this.connectorEndPointName = connectorEndPointName;
    }
}
