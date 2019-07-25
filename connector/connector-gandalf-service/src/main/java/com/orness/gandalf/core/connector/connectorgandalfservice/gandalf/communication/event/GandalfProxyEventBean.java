
package com.orness.gandalf.core.connector.connectorgandalfservice.gandalf.communication.event;

import com.orness.gandalf.core.module.zeromqmodule.event.proxy.PubSubProxyZeroMQ;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class GandalfProxyEventBean implements Runnable {

    private String subscriber;
    private String publisher;

    public GandalfProxyEventBean(@Value("${gandalf.communication.subscriber}") String subscriber, @Value("${gandalf.communication.publisher}") String publisher) {
        this.subscriber = subscriber;
        this.publisher = publisher;
    }

    @Override
    public void run() {
        new PubSubProxyZeroMQ(subscriber, publisher);
    }
}

