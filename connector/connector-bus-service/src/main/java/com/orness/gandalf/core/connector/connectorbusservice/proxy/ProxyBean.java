
package com.orness.gandalf.core.connector.connectorbusservice.proxy;

import com.orness.gandalf.core.module.zeromqmodule.proxy.PubSubProxyZeroMQ;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ProxyBean implements Runnable {

    @Override
    public void run() {
        new PubSubProxyZeroMQ("ipc://sub", "ipc://pub");
    }
}

