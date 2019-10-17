package com.ditrit.gandalf.core.clientcore;

import com.ditrit.gandalf.core.clientcore.properties.ClientProperties;
import com.ditrit.gandalf.core.zeromqcore.library.client.PublisherZeroMQ;
import com.ditrit.gandalf.core.zeromqcore.library.client.ThreadClientZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "client")
@Scope("singleton")
public class Client {

    private ClientProperties clientProperties;
    private ThreadClientZeroMQ threadClientZeroMQ;
    private PublisherZeroMQ publisherZeroMQ;


    @Autowired
    public Client(ClientProperties clientProperties) {
        this.clientProperties = clientProperties;
        this.threadClientZeroMQ = new ThreadClientZeroMQ(this.clientProperties.getConnectorName(), this.clientProperties.getConnectorCommandBackEndSendConnection());
        this.publisherZeroMQ = new PublisherZeroMQ(this.clientProperties.getConnectorName(), this.clientProperties.getConnectorEventBackEndSendConnection());
    }

    public ZMsg sendCommandSync(String context, String timeout, String uuid, String command, String payload) {
        return this.threadClientZeroMQ.sendCommandSync(context, timeout, uuid, command, payload);
    }

    public void sendCommandAsync(String context, String timeout, String uuid, String command, String payload) {
        if(this.threadClientZeroMQ.isInterrupted()) {
            this.threadClientZeroMQ.start();
        }
        this.threadClientZeroMQ.sendCommandAsync(context, timeout, uuid, command, payload);
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
