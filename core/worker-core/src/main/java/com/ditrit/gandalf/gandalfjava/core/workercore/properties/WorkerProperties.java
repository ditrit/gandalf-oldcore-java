package com.ditrit.gandalf.gandalfjava.core.workercore.properties;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class WorkerProperties {

    private Gson mapper;

    @Value("${com.ditrit.gandalf.gandalfjava.core.workercore.controller.worker.name}")
    private String workerName;
    @Value("${com.ditrit.gandalf.gandalfjava.core.workercore.controller.worker.type}")
    private String workerType;
    @Value("${com.ditrit.gandalf.gandalfjava.core.workercore.controller.worker.target.endpoint}")
    private String workerTargetConnection;
    @Value("${com.ditrit.gandalf.gandalfjava.core.workercore.controller.worker.service.endpoint}")
    private String workerServiceConnection;
    @Value("${com.ditrit.gandalf.gandalfjava.core.workercore.controller.worker.command.connection}")
    private String workerCommandFrontEndReceiveConnection;
    @Value("${com.ditrit.gandalf.gandalfjava.core.workercore.controller.worker.event.connection}")
    private String workerEventFrontEndReceiveConnection;

    @Autowired
    public WorkerProperties() {
        this.mapper = new Gson();
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
