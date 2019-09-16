package com.orness.gandalf.core.module.listenercore;

import com.orness.gandalf.core.module.listenercore.properties.ListenerProperties;
import com.orness.gandalf.core.module.zeromqcore.command.listener.ThreadListenerCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqcore.event.listener.ThreadListenerEventZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "gandalfListener")
public class GandalfListener {

    private ListenerProperties listenerProperties;
    private ThreadListenerCommandZeroMQ threadListenerCommandZeroMQ;
    private ThreadListenerEventZeroMQ threadListenerEventZeroMQ;

    @Autowired
    public GandalfListener(ListenerProperties listenerProperties) {
        this.listenerProperties = listenerProperties;
        this.threadListenerCommandZeroMQ = new ThreadListenerCommandZeroMQ(this.listenerProperties.getConnectorName(), this.listenerProperties.getListenerCommandBackEndConnections());
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

    public void sendCommandResult(ZMsg command, boolean result) {
        this.threadListenerCommandZeroMQ.sendResultCommand(command, result);
    }
}
