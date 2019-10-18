package com.ditrit.gandalf.jobs.registerjob.service;

import com.ditrit.gandalf.jobs.registerjob.properties.RegisterJobProperties;
import com.ditrit.gandalf.library.gandalfclient.GandalfClient;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zeromq.ZMsg;

import java.util.UUID;

@Service
public class RegisterJobService {

    private GandalfClient gandalfClient;
    private RegisterJobProperties registerJobProperties;

    @Autowired
    public RegisterJobService(GandalfClient gandalfClient, RegisterJobProperties registerJobProperties) {
        this.gandalfClient = gandalfClient;
        this.registerJobProperties = registerJobProperties;
    }

    public ZMsg sendCommand(String command, JsonObject payload) {
        return this.gandalfClient.getClient().sendCommandSync(UUID.randomUUID().toString(), this.registerJobProperties.getConnectorEndPointName(), "WORKER_SERVICE_CLASS_STANDARD", command, "5", payload.toString());
    }

    public void sendEvent(String topic, String event, String payload) {
        this.gandalfClient.getClient().sendEvent(topic, event, "5",payload);
    }
}
