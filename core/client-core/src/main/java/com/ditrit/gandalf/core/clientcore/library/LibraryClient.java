/*package com.ditrit.gandalf.core.clientcore.library;

import com.ditrit.gandalf.core.clientcore.library.properties.LibraryClientProperties;
import com.ditrit.gandalf.core.zeromqcore.command.client.ThreadClientZeroMQ;
import com.ditrit.gandalf.core.zeromqcore.event.client.PublisherZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "libraryClient")
public class LibraryClient {

    private LibraryClientProperties libraryClientProperties;
    private ThreadClientZeroMQ threadClientZeroMQ;
    private PublisherZeroMQ publisherZeroMQ;


    @Autowired
    public LibraryClient(LibraryClientProperties libraryClientProperties) {
        this.libraryClientProperties = libraryClientProperties;
        this.threadClientZeroMQ = new ThreadClientZeroMQ(this.libraryClientProperties.getConnectorName(), this.libraryClientProperties.getConnectorCommandBackEndSendConnection());
        this.publisherZeroMQ = new PublisherZeroMQ(this.libraryClientProperties.getConnectorName(), this.libraryClientProperties.getConnectorEventBackEndSendConnection());
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
}*/
