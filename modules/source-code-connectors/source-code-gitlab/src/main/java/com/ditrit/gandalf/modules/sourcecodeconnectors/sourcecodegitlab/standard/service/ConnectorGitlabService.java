package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.standard.service;

import com.ditrit.gandalf.library.gandalfclient.GandalfClient;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zeromq.ZMsg;

import java.util.UUID;

@Service
public class ConnectorGitlabService {

    private GandalfClient gandalfClient;

    @Autowired
    public ConnectorGitlabService(GandalfClient gandalfClient) {
        this.gandalfClient = gandalfClient;
    }

    public ZMsg sendCommand(String connectorTarget, String workerTarget, String command, JsonObject payload) {
        return this.gandalfClient.getClient().sendCommandSync(UUID.randomUUID().toString(), connectorTarget, workerTarget, command, "5", payload.toString());
    }

    public void sendEvent(String topic, String event, JsonObject payload) {
        this.gandalfClient.getClient().sendEvent(topic, event, "5",payload.toString());
    }
}
