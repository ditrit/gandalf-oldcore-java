
package com.orness.gandalf.core.connector.connectorbusservice.proxy;

import com.orness.gandalf.core.module.zeromqmodule.event.proxy.PubSubProxyZeroMQ;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ProxyBean implements Runnable {

    private String subscriber;
    private String publisher;

    public ProxyBean(@Value("${gandalf.bus.subscriber}") String subscriber, @Value("${gandalf.bus.publisher}") String publisher) {
        this.subscriber = subscriber;
        this.publisher = publisher;
    }

    @Override
    public void run() {
        new PubSubProxyZeroMQ(subscriber, publisher);
    }
}

