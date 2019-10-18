package com.ditrit.gandalf.jobs.deployjob.service;

import com.ditrit.gandalf.jobs.deployjob.properties.DeployJobProperties;
import com.ditrit.gandalf.library.gandalfclient.GandalfClient;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zeromq.ZMsg;

import java.util.UUID;

@Service
public class DeployJobService {

    private GandalfClient gandalfClient;
    private DeployJobProperties deployJobProperties;

    @Autowired
    public DeployJobService(GandalfClient gandalfClient, DeployJobProperties deployJobProperties) {
        this.gandalfClient = gandalfClient;
        this.deployJobProperties = deployJobProperties;
    }

    public ZMsg sendCommand(String connectorTarget, String workerTarget, String command, JsonObject payload) {
        return this.gandalfClient.getClient().sendCommandSync(UUID.randomUUID().toString(), connectorTarget, workerTarget, command, "5", payload.toString());
    }

    public void sendEvent(String topic, String event, String payload) {
        this.gandalfClient.getClient().sendEvent(topic, event, "5",payload);
    }

}
