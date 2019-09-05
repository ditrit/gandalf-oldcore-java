package com.orness.gandalf.core.module.clientcore;

import com.orness.gandalf.core.module.clientcore.client.ClientCommand;
import com.orness.gandalf.core.module.clientcore.client.ClientEvent;
import com.orness.gandalf.core.module.clientcore.properties.ClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "gandalfClient")
public class GandalfClient {

    private ClientProperties clientProperties;
    private ClientCommand clientCommand;
    private ClientEvent clientEvent;

    public ClientCommand getClientCommand() {
        return clientCommand;
    }

    @Autowired
    public GandalfClient(ClientProperties clientProperties) {
        this.clientProperties = clientProperties;
        this.clientCommand = new ClientCommand(this.clientProperties);
        this.clientEvent = new ClientEvent(this.clientProperties);
    }

    public void sendCommand(String uuid, String connector, String serviceClass, String command, String payload) {
        this.clientCommand.sendCommand(uuid, connector, serviceClass, command, payload);
    }

    public ZMsg getCommandResult() {
        return this.clientCommand.getLastReponses();
    }

    public void sendEvent(String topic, String event, String payload) {
        this.clientEvent.sendEvent(topic, event, payload);
    }
}
