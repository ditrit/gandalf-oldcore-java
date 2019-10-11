package com.ditrit.gandalf.core.clientcore.custom;

import com.ditrit.gandalf.core.clientcore.custom.properties.CustomClientProperties;
import com.ditrit.gandalf.core.zeromqcore.command.client.ThreadClientZeroMQ;
import com.ditrit.gandalf.core.zeromqcore.event.client.PublisherZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "customClient")
public class CustomClient {

    private CustomClientProperties customClientProperties;
    private ThreadClientZeroMQ threadClientZeroMQ;
    private PublisherZeroMQ publisherZeroMQ;


    @Autowired
    public CustomClient(CustomClientProperties customClientProperties) {
        this.customClientProperties = customClientProperties;
        this.threadClientZeroMQ = new ThreadClientZeroMQ(this.customClientProperties.getConnectorName(), this.customClientProperties.getClientCommandBackEndConnections());
        this.publisherZeroMQ = new PublisherZeroMQ(this.customClientProperties.getConnectorName(), this.customClientProperties.getConnectorCommandBackEndSendConnection());
    }

    public ZMsg sendCommandSync(String uuid, String connector, String serviceClass, String command, String timeout, String payload) {
        return this.threadClientZeroMQ.sendCustomCommandSync(uuid, connector, serviceClass, command, timeout, payload);
    }

    public void sendCommandAsync(String uuid, String connector, String serviceClass, String command, String timeout, String payload) {
        if(this.threadClientZeroMQ.isInterrupted()) {
            this.threadClientZeroMQ.start();
        }
        this.threadClientZeroMQ.sendCustomCommandAsync(uuid, connector, serviceClass, command, timeout, payload);
    }

    public ZMsg getCommandResultAsync() {
        if(this.threadClientZeroMQ.isInterrupted()) {
            this.threadClientZeroMQ.start();
        }
        return this.threadClientZeroMQ.getCommandResultAsync();
    }

    public void sendEvent(String topic, String event, String timeout, String payload) {
        this.publisherZeroMQ.sendEvent(topic, event, timeout, payload);
    }
}
