package com.orness.gandalf.core.module.jenkinsmodule.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile(value = "jenkins")
public class ConnectorJenkinsProperties {

    @Value("${${connector.name}.${connector.type}.${connector.name}.connectorBackEndConnection}")
    private String connectorBackEndConnection;
    @Value("${${connector.name}.${connector.type}.${connector.name}.topics}")
    private List<String> topics;

    public String getConnectorBackEndConnection() {
        return connectorBackEndConnection;
    }

    public void setConnectorBackEndConnection(String connectorBackEndConnection) {
        this.connectorBackEndConnection = connectorBackEndConnection;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
