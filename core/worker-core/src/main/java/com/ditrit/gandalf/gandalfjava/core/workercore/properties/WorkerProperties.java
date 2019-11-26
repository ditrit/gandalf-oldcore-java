package com.ditrit.gandalf.gandalfjava.core.workercore.properties;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class WorkerProperties {

    private Gson mapper;

    @Value("${connector.name}")
    private String connectorName;
    @Value("${worker.name}")
    private String workerName;
    @Value("${worker.type}")
    private String workerType;
    @Value("${worker.target.endpoint}")
    private String workerTargetConnection;
    @Value("${worker.service.endpoint}")
    private String workerServiceConnection;
    @Value("${worker.command.connection}")
    private String workerCommandFrontEndReceiveConnection;
    @Value("${worker.event.connection}")
    private String workerEventFrontEndReceiveConnection;

    @Autowired
    public WorkerProperties() {
        this.mapper = new Gson();
    }

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerType() {
        return workerType;
    }

    public void setWorkerType(String workerType) {
        this.workerType = workerType;
    }

    public String getWorkerTargetConnection() {
        return workerTargetConnection;
    }

    public void setWorkerTargetConnection(String workerTargetConnection) {
        this.workerTargetConnection = workerTargetConnection;
    }

    public String getWorkerCommandFrontEndReceiveConnection() {
        return workerCommandFrontEndReceiveConnection;
    }

    public void setWorkerCommandFrontEndReceiveConnection(String workerCommandFrontEndReceiveConnection) {
        this.workerCommandFrontEndReceiveConnection = workerCommandFrontEndReceiveConnection;
    }

    public String getWorkerEventFrontEndReceiveConnection() {
        return workerEventFrontEndReceiveConnection;
    }

    public void setWorkerEventFrontEndReceiveConnection(String workerEventFrontEndReceiveConnection) {
        this.workerEventFrontEndReceiveConnection = workerEventFrontEndReceiveConnection;
    }

    public String getWorkerServiceConnection() {
        return workerServiceConnection;
    }

    public void setWorkerServiceConnection(String workerServiceConnection) {
        this.workerServiceConnection = workerServiceConnection;
    }
}
