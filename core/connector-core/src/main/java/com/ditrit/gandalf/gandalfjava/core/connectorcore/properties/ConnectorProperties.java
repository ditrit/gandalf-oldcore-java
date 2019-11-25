package com.ditrit.gandalf.gandalfjava.core.connectorcore.properties;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class ConnectorProperties {

    private Gson mapper;

    @Value("${instance.name}")
    private String instanceName;
    @Value("${aggregator.name}")
    private String aggregatorName;
    @Value("${connector.name}")
    private String connectorName;
    @Value("${connector.type}")
    private String connectorType;

    private String targetEndPointConnection;
    private String targetType;
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

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public String getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
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


    public String getTargetEndPointConnection() {
        return targetEndPointConnection;
    }

    public void setTargetEndPointConnection(String targetEndPointConnection) {
        this.targetEndPointConnection = targetEndPointConnection;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
