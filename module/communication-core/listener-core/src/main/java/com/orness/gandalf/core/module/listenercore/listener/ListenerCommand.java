package com.orness.gandalf.core.module.listenercore.listener;


import com.orness.gandalf.core.module.listenercore.properties.ListenerProperties;
import com.orness.gandalf.core.module.zeromqcore.command.listener.ThreadListenerCommandZeroMQ;
import org.zeromq.ZMsg;

public class ListenerCommand {

    private ListenerProperties listenerProperties;
    private ThreadListenerCommandZeroMQ threadListenerCommandZeroMQ;

    public ListenerCommand(ListenerProperties listenerProperties) {
        this.listenerProperties = listenerProperties;
        this.threadListenerCommandZeroMQ = new ThreadListenerCommandZeroMQ(this.listenerProperties.getConnectorName(), this.listenerProperties.getListenerCommandBackEndConnections());
    }

    public ZMsg getCommandSync() {
        return this.threadListenerCommandZeroMQ.getCommandSync();
    }

    public void startCommandAsync() {
        this.threadListenerCommandZeroMQ.start();
    }

    public void stopCommandAsync() {
        this.threadListenerCommandZeroMQ.stop();
    }

    public ZMsg getCommandAsync() {
        return this.threadListenerCommandZeroMQ.getCommandAsync();
    }
}
