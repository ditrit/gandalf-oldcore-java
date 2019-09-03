package com.orness.gandalf.core.module.connectorcore.properties;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class ConnectorProperties {

    private RestTemplate restTemplate;
    private String clusterProperties;

    public ConnectorProperties() {
        this.restTemplate = new RestTemplate();
        this.clusterProperties = this.restTemplate.getForObject(" http://localhost:8500/v1/catalog/service/gandalf_cmd_backend", String.class);
    }

    @Value("${instance.name}")
    private String instanceName;
    @Value("${connector.name}")
    private String connectorName;
    @Value("${${instance.name}.${connector.type}.${connector.name}.connectorCommandBackEndConnection}")
    private String connectorCommandBackEndConnection;
    @Value("${${instance.name}.${connector.type}.${connector.name}.connectorEventBackEndConnection}")
    private String connectorEventBackEndConnection;
    //CLUSTER
    @Value("${${instance.name}.${connector.name}.connectorCommandFrontEndConnection}")
    private List<String> connectorCommandFrontEndConnection;
    @Value("${${instance.name}.${connector.name}.connectorEventFrontEndConnection}")
    private String connectorEventFrontEndConnection;

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

}
