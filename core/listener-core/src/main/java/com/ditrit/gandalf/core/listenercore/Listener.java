package com.ditrit.gandalf.core.listenercore;

import com.ditrit.gandalf.core.listenercore.properties.ListenerProperties;
import com.ditrit.gandalf.core.zeromqcore.library.listener.ThreadListenerCommandZeroMQ;
import com.ditrit.gandalf.core.zeromqcore.library.listener.ThreadListenerEventZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "listener")
@Scope("singleton")
public class Listener {

    private ListenerProperties listenerProperties;
    private ThreadListenerCommandZeroMQ threadListenerCommandZeroMQ;
    private ThreadListenerEventZeroMQ threadListenerEventZeroMQ;

    @Autowired
    public Listener(ListenerProperties listenerProperties) {
        this.listenerProperties = listenerProperties;
        this.threadListenerCommandZeroMQ = new ThreadListenerCommandZeroMQ(this.listenerProperties.getConnectorName(), this.listenerProperties.getListenerCommandBackEndConnection());
        this.threadListenerEventZeroMQ = new ThreadListenerEventZeroMQ(this.listenerProperties.getConnectorName(), this.listenerProperties.getListenerEventBackEndConnection());
    }

    public ZMsg getEventSync() {
        return this.threadListenerEventZeroMQ.getEventSync();
    }

    public ZMsg getEventAsync() {
        if(this.threadListenerEventZeroMQ.isInterrupted()) {
            this.threadListenerEventZeroMQ.start();
        }
        return this.threadListenerEventZeroMQ.getEventAsync();
    }

    public ZMsg getCommandSync() {
        return this.threadListenerCommandZeroMQ.getCommandSync();
    }

    public ZMsg getCommandAsync() {
        if(this.threadListenerCommandZeroMQ.isInterrupted()) {
            this.threadListenerCommandZeroMQ.start();
        }
        return this.threadListenerCommandZeroMQ.getCommandAsync();
    }
}
