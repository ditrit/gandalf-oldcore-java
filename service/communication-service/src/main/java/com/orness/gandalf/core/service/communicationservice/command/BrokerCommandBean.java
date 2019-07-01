
package com.orness.gandalf.core.service.communicationservice.command;

import com.orness.gandalf.core.module.zeromqmodule.command.broker.BrokerZeroMQ;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class BrokerCommandBean implements Runnable {

    private String client;
    private String worker;

    public BrokerCommandBean(@Value("${gandalf.communication.client}") String client, @Value("${gandalf.communication.worker}") String worker) {
        this.client = client;
        this.worker = worker;
    }

    @Override
    public void run() {
        new BrokerZeroMQ(client, worker);
    }
}

