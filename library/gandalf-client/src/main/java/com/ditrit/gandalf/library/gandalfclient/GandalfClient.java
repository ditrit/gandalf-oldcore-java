package com.ditrit.gandalf.library.gandalfclient;

import com.ditrit.gandalf.core.clientcore.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan("com.ditrit.gandalf.core.clientcore")
public class GandalfClient {

    private Client client;

    @Autowired
    public GandalfClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
}
