package com.orness.gandalf.core.module.connectorcore.properties;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.orness.gandalf.core.module.connectorcore.constant.ConnectorConstant.*;

public class ConnectorProperties {

    private RestTemplate restTemplate;
    private Gson mapper;

    @Value("${instance.name}")
    private String instanceName;
    @Value("${connector.name}")
    private String connectorName;
    @Value("${${instance.name}.${connector.type}.${connector.name}.connectorCommandBackEndConnection}")
    private String connectorCommandBackEndConnection;
    @Value("${${instance.name}.${connector.type}.${connector.name}.connectorEventBackEndConnection}")
    private String connectorEventBackEndConnection;
    @Value("${${instance.name}.${connector.type}.${connector.name}.topics}")
    private List<String> topics;

    private List<String> connectorCommandFrontEndConnection;
    private String connectorEventFrontEndConnection;

    public ConnectorProperties() {
        this.restTemplate = new RestTemplate();
        this.mapper = new Gson();
        this.initProperties();
    }

    private JsonArray restTemplateRequest(String clusterPropertiesName) {
        return mapper.fromJson(this.restTemplate.getForObject("http://localhost:8500/v1/catalog/service/"+clusterPropertiesName, String.class), JsonArray.class);
    }

    private void initProperties() {
        //CONNECTEUR COMMAND FRONT
        JsonArray currentclusterPropertiesJsonArray = this.restTemplateRequest(GANDALF_CLUSTER_COMMAND_BACKEND);
       this.connectorCommandFrontEndConnection = StreamSupport.stream(currentclusterPropertiesJsonArray.spliterator(), false)
               .map(JsonObject.class::cast)
               .map(o -> concatAddressPort(o.get(GANDALF_CLUSTER_ADDRESS), o.get(GANDALF_CLUSTER_PORT)))
               .collect(Collectors.toList());

        //CONNECTEUR EVENT FRONT
        currentclusterPropertiesJsonArray = this.restTemplateRequest(GANDALF_CLUSTER_EVENT_BACKEND);
        JsonObject currentclusterPropertiesJsonObject = currentclusterPropertiesJsonArray.get(0).getAsJsonObject();
        this.connectorEventFrontEndConnection = concatAddressPort(currentclusterPropertiesJsonObject.get(GANDALF_CLUSTER_ADDRESS), currentclusterPropertiesJsonObject.get(GANDALF_CLUSTER_PORT));
    }

    private String concatAddressPort(JsonElement address, JsonElement port) {
        return new StringBuilder("tcp://").append(address.getAsString()).append(":").append(port.getAsString()).toString();
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
