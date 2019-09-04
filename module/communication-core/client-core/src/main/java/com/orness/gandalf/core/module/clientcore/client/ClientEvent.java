package com.orness.gandalf.core.module.clientcore.client;

import com.orness.gandalf.core.module.clientcore.properties.ClientProperties;
import com.orness.gandalf.core.module.zeromqcore.event.client.PublisherZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class ClientEvent extends PublisherZeroMQ {

    private ClientProperties clientProperties;

    public ClientEvent(ClientProperties clientProperties) {
        super();
        this.clientProperties = clientProperties;
        this.init(this.clientProperties.getConnectorName(), this.clientProperties.getClientEventBackEndConnection());
    }
}
