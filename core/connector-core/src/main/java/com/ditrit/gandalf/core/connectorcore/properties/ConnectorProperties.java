package com.ditrit.gandalf.core.connectorcore.properties;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.ditrit.gandalf.core.connectorcore.constant.ConnectorConstant.*;

public class ConnectorProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.";

    private RestTemplate restTemplate;
    private Gson mapper;

    @Value("${instance.name}")
    private String instanceName;
    @Value("${connector.name}")
    private String connectorName;
    //@Value("${" + PROPERTIES_BASE + "topics}")
    private List<String> topics;

    @Value("${" + PROPERTIES_BASE + "connectorCommandBackEndSendConnection:tcp://*:9020}")
    private String connectorCommandBackEndSendConnection;
    @Value("${" + PROPERTIES_BASE + "connectorCommandBackEndReceiveConnection:tcp://*:9021}")
    private String connectorCommandBackEndReceiveConnection;
    @Value("${" + PROPERTIES_BASE + "connectorEventBackEndSendConnection:tcp://*:9030}")
    private String connectorEventBackEndSendConnection;
    @Value("${" + PROPERTIES_BASE + "connectorEventBackEndReceiveConnection:tcp://*:9031}")
    private String connectorEventBackEndReceiveConnection;

    private List<String> connectorCommandFrontEndReceiveConnections;
    private List<String> connectorCommandFrontEndSendConnections;
    private List<String> connectorEventFrontEndReceiveConnection;
    private String connectorEventFrontEndSendConnection;

    public ConnectorProperties() {
        this.restTemplate = new RestTemplate();
        this.mapper = new Gson();
        this.initProperties();
    }

    private JsonArray restTemplateRequest(String clusterPropertiesName) {
        return mapper.fromJson(this.restTemplate.getForObject("http://localhost:8500/v1/catalog/service/"+clusterPropertiesName, String.class), JsonArray.class);
    }

    private void initProperties() {
        //CONNECTEUR COMMAND RECEIVE FRONT
        JsonArray currentclusterPropertiesJsonArray = this.restTemplateRequest(GANDALF_CLUSTER_COMMAND_BACKEND);
        this.connectorCommandFrontEndReceiveConnections = StreamSupport.stream(currentclusterPropertiesJsonArray.spliterator(), false)
               .map(JsonObject.class::cast)
               .map(o -> concatFrontEndAddressPort(o.get(GANDALF_CLUSTER_ADDRESS), o.get(GANDALF_CLUSTER_PORT)))
               .collect(Collectors.toList());

        //CONNECTEUR COMMAND SEND FRONT
        currentclusterPropertiesJsonArray = this.restTemplateRequest(GANDALF_CLUSTER_COMMAND_FRONTEND);
        this.connectorCommandFrontEndSendConnections = StreamSupport.stream(currentclusterPropertiesJsonArray.spliterator(), false)
                .map(JsonObject.class::cast)
                .map(o -> concatFrontEndAddressPort(o.get(GANDALF_CLUSTER_ADDRESS), o.get(GANDALF_CLUSTER_PORT)))
                .collect(Collectors.toList());

        //CONNECTEUR EVENT RECEIVE FRONT
        currentclusterPropertiesJsonArray = this.restTemplateRequest(GANDALF_CLUSTER_EVENT_BACKEND);
        JsonObject currentclusterPropertiesJsonObject = currentclusterPropertiesJsonArray.get(0).getAsJsonObject();
        this.connectorEventFrontEndReceiveConnection = StreamSupport.stream(currentclusterPropertiesJsonArray.spliterator(), false)
                .map(JsonObject.class::cast)
                .map(o -> concatFrontEndAddressPort(o.get(GANDALF_CLUSTER_ADDRESS), o.get(GANDALF_CLUSTER_PORT)))
                .collect(Collectors.toList());

        //CONNECTEUR EVENT SEND FRONT
        currentclusterPropertiesJsonArray = this.restTemplateRequest(GANDALF_CLUSTER_EVENT_FRONTEND);
        currentclusterPropertiesJsonObject = currentclusterPropertiesJsonArray.get(0).getAsJsonObject();
        //this.connectorEventFrontEndSendConnection = concatFrontEndAddressPort(currentclusterPropertiesJsonObject.get(GANDALF_CLUSTER_ADDRESS), currentclusterPropertiesJsonObject.get(GANDALF_CLUSTER_PORT));
        this.connectorEventFrontEndSendConnection = concatFrontEndServicePort(currentclusterPropertiesJsonObject.get(GANDALF_CLUSTER_SERVICE), currentclusterPropertiesJsonObject.get(GANDALF_CLUSTER_PORT));

        //CONNECTEUR COMMAND BACK
        //this.connectorCommandBackEndConnection = concatBackEndAddressPort(currentclusterPropertiesJsonArray.get(0).getAsJsonObject().get(GANDALF_CLUSTER_PORT));

        //CONNECTEUR EVENT BACK
        //this.connectorEventBackEndConnection = concatBackEndAddressPort(currentclusterPropertiesJsonObject.get(GANDALF_CLUSTER_PORT));

    }

    private String concatFrontEndServicePort(JsonElement address, JsonElement port) {
        return new StringBuilder("tcp://").append(address.getAsString()).append(".service.gandalf:").append(port.getAsString()).toString();
    }

    private String concatFrontEndAddressPort(JsonElement address, JsonElement port) {
        return new StringBuilder("tcp://").append(address.getAsString()).append(":").append(port.getAsString()).toString();
    }

    private String concatBackEndAddressPort(JsonElement port) {
        return new StringBuilder("tcp://").append("*:").append(port.getAsString()).toString();
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

    public String getConnectorCommandBackEndSendConnection() {
        return connectorCommandBackEndSendConnection;
    }

    public void setConnectorCommandBackEndSendConnection(String connectorCommandBackEndSendConnection) {
        this.connectorCommandBackEndSendConnection = connectorCommandBackEndSendConnection;
    }

    public String getConnectorCommandBackEndReceiveConnection() {
        return connectorCommandBackEndReceiveConnection;
    }

    public void setConnectorCommandBackEndReceiveConnection(String connectorCommandBackEndReceiveConnection) {
        this.connectorCommandBackEndReceiveConnection = connectorCommandBackEndReceiveConnection;
    }

    public String getConnectorEventBackEndReceiveConnection() {
        return connectorEventBackEndReceiveConnection;
    }

    public void setConnectorEventBackEndReceiveConnection(String connectorEventBackEndReceiveConnection) {
        this.connectorEventBackEndReceiveConnection = connectorEventBackEndReceiveConnection;
    }

    public String getConnectorEventBackEndSendConnection() {
        return connectorEventBackEndSendConnection;
    }

    public void setConnectorEventBackEndSendConnection(String connectorEventBackEndSendConnection) {
        this.connectorEventBackEndSendConnection = connectorEventBackEndSendConnection;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public List<String> getConnectorCommandFrontEndReceiveConnections() {
        return connectorCommandFrontEndReceiveConnections;
    }

    public void setConnectorCommandFrontEndReceiveConnections(List<String> connectorCommandFrontEndReceiveConnections) {
        this.connectorCommandFrontEndReceiveConnections = connectorCommandFrontEndReceiveConnections;
    }

    public List<String> getConnectorCommandFrontEndSendConnections() {
        return connectorCommandFrontEndSendConnections;
    }

    public void setConnectorCommandFrontEndSendConnections(List<String> connectorCommandFrontEndSendConnections) {
        this.connectorCommandFrontEndSendConnections = connectorCommandFrontEndSendConnections;
    }

    public List<String> getConnectorEventFrontEndReceiveConnection() {
        return connectorEventFrontEndReceiveConnection;
    }

    public void setConnectorEventFrontEndReceiveConnection(List<String> connectorEventFrontEndReceiveConnection) {
        this.connectorEventFrontEndReceiveConnection = connectorEventFrontEndReceiveConnection;
    }

    public String getConnectorEventFrontEndSendConnection() {
        return connectorEventFrontEndSendConnection;
    }

    public void setConnectorEventFrontEndSendConnection(String connectorEventFrontEndSendConnection) {
        this.connectorEventFrontEndSendConnection = connectorEventFrontEndSendConnection;
    }
}
