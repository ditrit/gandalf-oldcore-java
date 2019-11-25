package com.ditrit.gandalf.gandalfjava.core.aggregatorcore.properties;

import com.ditrit.gandalf.gandalfjava.core.aggregatorcore.constant.AggregatorConstant;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AggregatorProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.aggregators.${aggregator.name}.";

    private RestTemplate restTemplate;
    private Gson mapper;

    @Value("${instance.name}")
    private String instanceName;
    @Value("${aggregator.name}")
    private String aggregatorName;
    //@Value("${" + PROPERTIES_BASE + "topics}")
    private List<String> topics;

    @Value("${" + PROPERTIES_BASE + "aggregatorCommandBackEndSendConnection:tcp://*:9000}")
    private String aggregatorCommandBackEndSendConnection;
    @Value("${" + PROPERTIES_BASE + "aggregatorCommandBackEndReceiveConnection:tcp://*:9001}")
    private String aggregatorCommandBackEndReceiveConnection;
    @Value("${" + PROPERTIES_BASE + "aggregatorEventBackEndSendConnection:tcp://*:9010}")
    private String aggregatorEventBackEndSendConnection;
    @Value("${" + PROPERTIES_BASE + "aggregatorEventBackEndReceiveConnection:tcp://*:9011}")
    private String aggregatorEventBackEndReceiveConnection;
    @Value("${" + PROPERTIES_BASE + "aggregatorListenerServiceConnection:tcp://*:9021}")
    private String aggregatorListenerServiceConnection;

    private List<String> aggregatorCommandFrontEndReceiveConnections;
    private List<String> aggregatorCommandFrontEndSendConnections;
    private List<String> aggregatorEventFrontEndReceiveConnection;
    private String aggregatorEventFrontEndSendConnection;
    private String aggregatorClientServiceConnection;


    public AggregatorProperties() {
        this.restTemplate = new RestTemplate();
        this.mapper = new Gson();
        this.initProperties();
    }

    private JsonArray restTemplateRequest(String clusterPropertiesName) {
        return mapper.fromJson(this.restTemplate.getForObject("http://localhost:8500/v1/catalog/service/"+clusterPropertiesName, String.class), JsonArray.class);
    }

    private void initProperties() {
        //CONNECTEUR COMMAND RECEIVE FRONT
        JsonArray currentclusterPropertiesJsonArray = this.restTemplateRequest(AggregatorConstant.GANDALF_CLUSTER_COMMAND_BACKEND);
        this.aggregatorCommandFrontEndReceiveConnections = StreamSupport.stream(currentclusterPropertiesJsonArray.spliterator(), false)
               .map(JsonObject.class::cast)
               .map(o -> concatFrontEndAddressPort(o.get(AggregatorConstant.GANDALF_CLUSTER_ADDRESS), o.get(AggregatorConstant.GANDALF_CLUSTER_PORT)))
               .collect(Collectors.toList());

        //CONNECTEUR COMMAND SEND FRONT
        currentclusterPropertiesJsonArray = this.restTemplateRequest(AggregatorConstant.GANDALF_CLUSTER_COMMAND_FRONTEND);
        this.aggregatorCommandFrontEndSendConnections = StreamSupport.stream(currentclusterPropertiesJsonArray.spliterator(), false)
                .map(JsonObject.class::cast)
                .map(o -> concatFrontEndAddressPort(o.get(AggregatorConstant.GANDALF_CLUSTER_ADDRESS), o.get(AggregatorConstant.GANDALF_CLUSTER_PORT)))
                .collect(Collectors.toList());

        //CONNECTEUR EVENT RECEIVE FRONT
        currentclusterPropertiesJsonArray = this.restTemplateRequest(AggregatorConstant.GANDALF_CLUSTER_EVENT_BACKEND);
        JsonObject currentclusterPropertiesJsonObject = currentclusterPropertiesJsonArray.get(0).getAsJsonObject();
        this.aggregatorEventFrontEndReceiveConnection = StreamSupport.stream(currentclusterPropertiesJsonArray.spliterator(), false)
                .map(JsonObject.class::cast)
                .map(o -> concatFrontEndAddressPort(o.get(AggregatorConstant.GANDALF_CLUSTER_ADDRESS), o.get(AggregatorConstant.GANDALF_CLUSTER_PORT)))
                .collect(Collectors.toList());

        //CONNECTEUR EVENT SEND FRONT
        currentclusterPropertiesJsonArray = this.restTemplateRequest(AggregatorConstant.GANDALF_CLUSTER_EVENT_FRONTEND);
        currentclusterPropertiesJsonObject = currentclusterPropertiesJsonArray.get(0).getAsJsonObject();
        this.aggregatorEventFrontEndSendConnection = concatFrontEndServicePort(currentclusterPropertiesJsonObject.get(AggregatorConstant.GANDALF_CLUSTER_SERVICE), currentclusterPropertiesJsonObject.get(AggregatorConstant.GANDALF_CLUSTER_PORT));

        //CONNECTEUR SERVICE SEND FRONT
        currentclusterPropertiesJsonArray = this.restTemplateRequest(AggregatorConstant.GANDALF_CLUSTER_SERVICE_FRONTEND);
        currentclusterPropertiesJsonObject = currentclusterPropertiesJsonArray.get(0).getAsJsonObject();
        this.aggregatorClientServiceConnection = concatFrontEndServicePort(currentclusterPropertiesJsonObject.get(AggregatorConstant.GANDALF_CLUSTER_SERVICE), currentclusterPropertiesJsonObject.get(AggregatorConstant.GANDALF_CLUSTER_PORT));
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

    public String getAggregatorName() {
        return aggregatorName;
    }

    public void setAggregatorName(String aggregatorName) {
        this.aggregatorName = aggregatorName;
    }

    public String getAggregatorCommandBackEndSendConnection() {
        return aggregatorCommandBackEndSendConnection;
    }

    public void setAggregatorCommandBackEndSendConnection(String aggregatorCommandBackEndSendConnection) {
        this.aggregatorCommandBackEndSendConnection = aggregatorCommandBackEndSendConnection;
    }

    public String getAggregatorCommandBackEndReceiveConnection() {
        return aggregatorCommandBackEndReceiveConnection;
    }

    public void setAggregatorCommandBackEndReceiveConnection(String aggregatorCommandBackEndReceiveConnection) {
        this.aggregatorCommandBackEndReceiveConnection = aggregatorCommandBackEndReceiveConnection;
    }

    public String getAggregatorEventBackEndSendConnection() {
        return aggregatorEventBackEndSendConnection;
    }

    public void setAggregatorEventBackEndSendConnection(String aggregatorEventBackEndSendConnection) {
        this.aggregatorEventBackEndSendConnection = aggregatorEventBackEndSendConnection;
    }

    public String getAggregatorEventBackEndReceiveConnection() {
        return aggregatorEventBackEndReceiveConnection;
    }

    public void setAggregatorEventBackEndReceiveConnection(String aggregatorEventBackEndReceiveConnection) {
        this.aggregatorEventBackEndReceiveConnection = aggregatorEventBackEndReceiveConnection;
    }

    public String getAggregatorClientServiceConnection() {
        return aggregatorClientServiceConnection;
    }

    public void setAggregatorClientServiceConnection(String aggregatorClientServiceConnection) {
        this.aggregatorClientServiceConnection = aggregatorClientServiceConnection;
    }

    public String getAggregatorListenerServiceConnection() {
        return aggregatorListenerServiceConnection;
    }

    public void setAggregatorListenerServiceConnection(String aggregatorListenerServiceConnection) {
        this.aggregatorListenerServiceConnection = aggregatorListenerServiceConnection;
    }

    public List<String> getAggregatorCommandFrontEndReceiveConnections() {
        return aggregatorCommandFrontEndReceiveConnections;
    }

    public void setAggregatorCommandFrontEndReceiveConnections(List<String> aggregatorCommandFrontEndReceiveConnections) {
        this.aggregatorCommandFrontEndReceiveConnections = aggregatorCommandFrontEndReceiveConnections;
    }

    public List<String> getAggregatorCommandFrontEndSendConnections() {
        return aggregatorCommandFrontEndSendConnections;
    }

    public void setAggregatorCommandFrontEndSendConnections(List<String> aggregatorCommandFrontEndSendConnections) {
        this.aggregatorCommandFrontEndSendConnections = aggregatorCommandFrontEndSendConnections;
    }

    public List<String> getAggregatorEventFrontEndReceiveConnection() {
        return aggregatorEventFrontEndReceiveConnection;
    }

    public void setAggregatorEventFrontEndReceiveConnection(List<String> aggregatorEventFrontEndReceiveConnection) {
        this.aggregatorEventFrontEndReceiveConnection = aggregatorEventFrontEndReceiveConnection;
    }

    public String getAggregatorEventFrontEndSendConnection() {
        return aggregatorEventFrontEndSendConnection;
    }

    public void setAggregatorEventFrontEndSendConnection(String aggregatorEventFrontEndSendConnection) {
        this.aggregatorEventFrontEndSendConnection = aggregatorEventFrontEndSendConnection;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
