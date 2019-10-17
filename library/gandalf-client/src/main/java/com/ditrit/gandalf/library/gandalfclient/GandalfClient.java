package com.ditrit.gandalf.library.gandalfclient;

import com.ditrit.gandalf.core.clientcore.Client;
import com.ditrit.gandalf.core.listenercore.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages = {"com.ditrit.gandalf.core.clientcore", "com.ditrit.gandalf.library.listenercore"})
public class GandalfClient {

    private Client client;
    private Listener listener;

    @Autowired
    public GandalfClient(Client client, Listener listener) {
        this.client = client;
        this.listener = listener;
    }

    public Client getClient() {
        return client;
    }

    public Listener getListener() {
        return listener;
    }
}
