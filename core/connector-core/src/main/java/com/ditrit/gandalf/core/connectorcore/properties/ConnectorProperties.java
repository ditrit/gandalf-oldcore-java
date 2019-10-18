package com.ditrit.gandalf.core.connectorcore.properties;

import com.ditrit.gandalf.core.connectorcore.service.ConnectorClientService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.zeromq.ZMsg;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.ditrit.gandalf.core.connectorcore.constant.ConnectorConstant.*;

public class ConnectorProperties {

    private ConnectorClientService connectorClientService;
    private Gson mapper;

    @Value("${instance.name}")
    private String instanceName;
    @Value("${aggregator.name}")
    private String aggregatorName;
    @Value("${connector.name}")
    private String connectorName;
    //@Value("${" + PROPERTIES_BASE + "topics}")
    private List<String> topics;

    @Value("tcp://*:9000")
    private String connectorCommandBackEndSendConnection;
    @Value("tcp://*:9001")
    private String connectorCommandBackEndReceiveConnection;
    @Value("tcp://*:9010")
    private String connectorEventBackEndSendConnection;
    @Value("tcp://*:9011")
    private String connectorEventBackEndReceiveConnection;
    @Value("tcp://*:9021")
    private String connectorListenerServiceConnection;

    @Value("${aggregator.service.endpoint}")
    private String connectorClientServiceConnection;
    private String connectorCommandFrontEndReceiveConnection;
    private String connectorCommandFrontEndSendConnection;
    private String connectorEventFrontEndReceiveConnection;
    private String connectorEventFrontEndSendConnection;

    public ConnectorProperties() {
        this.mapper = new Gson();
        this.initProperties();
    }

    private void initProperties() {

        ZMsg response = this.connectorClientService.sendRequest("configuration");
        Object[] responseConnections =  response.toArray();

        this.connectorCommandFrontEndReceiveConnection = responseConnections[0].toString();
        this.connectorCommandFrontEndSendConnection = responseConnections[1].toString();
        this.connectorEventFrontEndReceiveConnection = responseConnections[2].toString();
        this.connectorEventFrontEndSendConnection = responseConnections[3].toString();
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

    public String getConnectorClientServiceConnection() {
        return connectorClientServiceConnection;
    }

    public void setConnectorClientServiceConnection(String connectorClientServiceConnection) {
        this.connectorClientServiceConnection = connectorClientServiceConnection;
    }

    public String getConnectorListenerServiceConnection() {
        return connectorListenerServiceConnection;
    }

    public void setConnectorListenerServiceConnection(String connectorListenerServiceConnection) {
        this.connectorListenerServiceConnection = connectorListenerServiceConnection;
    }

    public String getConnectorCommandFrontEndReceiveConnection() {
        return connectorCommandFrontEndReceiveConnection;
    }

    public void setConnectorCommandFrontEndReceiveConnection(String connectorCommandFrontEndReceiveConnection) {
        this.connectorCommandFrontEndReceiveConnection = connectorCommandFrontEndReceiveConnection;
    }

    public String getConnectorCommandFrontEndSendConnection() {
        return connectorCommandFrontEndSendConnection;
    }

    public void setConnectorCommandFrontEndSendConnection(String connectorCommandFrontEndSendConnection) {
        this.connectorCommandFrontEndSendConnection = connectorCommandFrontEndSendConnection;
    }

    public String getConnectorEventFrontEndReceiveConnection() {
        return connectorEventFrontEndReceiveConnection;
    }

    public void setConnectorEventFrontEndReceiveConnection(String connectorEventFrontEndReceiveConnection) {
        this.connectorEventFrontEndReceiveConnection = connectorEventFrontEndReceiveConnection;
    }

    public String getConnectorEventFrontEndSendConnection() {
        return connectorEventFrontEndSendConnection;
    }

    public void setConnectorEventFrontEndSendConnection(String connectorEventFrontEndSendConnection) {
        this.connectorEventFrontEndSendConnection = connectorEventFrontEndSendConnection;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
