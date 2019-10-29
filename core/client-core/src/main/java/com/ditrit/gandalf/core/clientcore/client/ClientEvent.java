package com.ditrit.gandalf.core.clientcore.client;

import com.ditrit.gandalf.core.clientcore.properties.ClientProperties;
import com.ditrit.gandalf.core.zeromqcore.library.client.PublisherZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value = "clientEvent")
@Scope("singleton")
public class ClientEvent extends PublisherZeroMQ {

    private ClientProperties clientProperties;

    @Autowired
    public ClientEvent(ClientProperties clientProperties) {
        this.clientProperties = clientProperties;
        this.init(this.clientProperties.getConnectorName(), this.clientProperties.getConnectorEventBackEndSendConnection());
    }
}
