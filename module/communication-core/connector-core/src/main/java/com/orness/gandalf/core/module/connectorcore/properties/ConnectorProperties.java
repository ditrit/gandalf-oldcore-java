package com.orness.gandalf.core.module.connectorcore.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
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
    @Value("${${connector.name}.${connector.type}.${connector.name}.topics}")
    private List<String> topics;

    //CLUSTER
    @Value("${${instance.name}.${connector.name}.connectorCommandFrontEndConnection}")
    private List<String> connectorCommandFrontEndConnection;
    @Value("${${instance.name}.${connector.name}.connectorEventFrontEndConnection}")
    private String connectorEventFrontEndConnection;

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getClusterProperties() {
        return clusterProperties;
    }

    public void setClusterProperties(String clusterProperties) {
        this.clusterProperties = clusterProperties;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public String getConnectorCommandBackEndConnection() {
        return connectorCommandBackEndConnection;
    }

    public void setConnectorCommandBackEndConnection(String connectorCommandBackEndConnection) {
        this.connectorCommandBackEndConnection = connectorCommandBackEndConnection;
    }

    public String getConnectorEventBackEndConnection() {
        return connectorEventBackEndConnection;
    }

    public void setConnectorEventBackEndConnection(String connectorEventBackEndConnection) {
        this.connectorEventBackEndConnection = connectorEventBackEndConnection;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public List<String> getConnectorCommandFrontEndConnection() {
        return connectorCommandFrontEndConnection;
    }

    public void setConnectorCommandFrontEndConnection(List<String> connectorCommandFrontEndConnection) {
        this.connectorCommandFrontEndConnection = connectorCommandFrontEndConnection;
    }

    public String getConnectorEventFrontEndConnection() {
        return connectorEventFrontEndConnection;
    }

    public void setConnectorEventFrontEndConnection(String connectorEventFrontEndConnection) {
        this.connectorEventFrontEndConnection = connectorEventFrontEndConnection;
    }
}
