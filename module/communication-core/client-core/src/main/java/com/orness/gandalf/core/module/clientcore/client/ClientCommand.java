package com.orness.gandalf.core.module.clientcore.client;

import com.orness.gandalf.core.module.clientcore.properties.ClientProperties;
import com.orness.gandalf.core.module.zeromqcore.command.client.RunnableClientZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class ClientCommand extends RunnableClientZeroMQ {

    private ClientProperties clientProperties;

    public ClientCommand(ClientProperties clientProperties) {
        super();
        this.clientProperties = clientProperties;
        this.initRunnable(this.clientProperties.getConnectorName(), this.clientProperties.getClientCommandBackEndConnections());
    }
}
