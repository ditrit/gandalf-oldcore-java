package com.orness.gandalf.core.module.connectorcore.properties;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.orness.gandalf.core.module.connectorcore.constant.ConnectorConstant.*;

@Configuration
public class ConnectorProperties {

    private RestTemplate restTemplate;

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

    private List<String> connectorCommandFrontEndConnection;
    private String connectorEventFrontEndConnection;

    public ConnectorProperties() {
        this.restTemplate = new RestTemplate();
        this.initProperties();
    }

    private JsonObject restTemplateRequest(String clusterPropertiesName) {
        return this.restTemplate.getForObject(" http://localhost:8500/v1/catalog/service/"+clusterPropertiesName, JsonObject.class);
    }

    private void initProperties() {
        //CONNECTEUR COMMAND FRONT
        JsonObject currentclusterPropertiesJsonObject = this.restTemplateRequest(GANDALF_CLUSTER_COMMAND_BACKEND);
       this.connectorCommandFrontEndConnection = StreamSupport.stream(currentclusterPropertiesJsonObject.getAsJsonArray("res").spliterator(), false)
               .map(JsonObject.class::cast)
               .map(o -> concatAddressPort(o.get(GANDALF_CLUSTER_ADDRESS), o.get(GANDALF_CLUSTER_PORT)))
               .collect(Collectors.toList());

        //CONNECTEUR EVENT FRONT
        currentclusterPropertiesJsonObject = this.restTemplateRequest(GANDALF_CLUSTER_EVENT_BACKEND).getAsJsonObject("res");
        this.connectorEventFrontEndConnection = concatAddressPort(currentclusterPropertiesJsonObject.get(GANDALF_CLUSTER_ADDRESS), currentclusterPropertiesJsonObject.get(GANDALF_CLUSTER_PORT));
    }

    private String concatAddressPort(JsonElement address, JsonElement port) {
        return new StringBuilder(address.getAsString()).append(":").append(port.getAsString()).toString();
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
