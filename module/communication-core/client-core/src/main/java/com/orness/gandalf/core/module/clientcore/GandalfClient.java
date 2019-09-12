package com.orness.gandalf.core.module.clientcore;

import com.orness.gandalf.core.module.clientcore.properties.ClientProperties;
import com.orness.gandalf.core.module.zeromqcore.command.client.ThreadClientZeroMQ;
import com.orness.gandalf.core.module.zeromqcore.event.client.PublisherZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "gandalfClient")
public class GandalfClient {

    private ClientProperties clientProperties;
    private ThreadClientZeroMQ threadClientZeroMQ;
    private PublisherZeroMQ publisherZeroMQ;


    @Autowired
    public GandalfClient(ClientProperties clientProperties) {
        this.clientProperties = clientProperties;
        this.threadClientZeroMQ = new ThreadClientZeroMQ(this.clientProperties.getConnectorName(), this.clientProperties.getClientCommandBackEndConnections());
        this.publisherZeroMQ = new PublisherZeroMQ(this.clientProperties.getConnectorName(), this.clientProperties.getClientEventBackEndConnection());
    }

    public ZMsg sendCommandSync(String uuid, String connector, String serviceClass, String command, String timeout, String payload) {
        return this.threadClientZeroMQ.sendCommandSync(uuid, connector, serviceClass, command, timeout, payload);
    }

    public void sendCommandAsync(String uuid, String connector, String serviceClass, String command, String timeout, String payload) {
        if(this.threadClientZeroMQ.isInterrupted()) {
            this.threadClientZeroMQ.start();
        }
        this.threadClientZeroMQ.sendCommandAsync(uuid, connector, serviceClass, command, timeout, payload);
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
