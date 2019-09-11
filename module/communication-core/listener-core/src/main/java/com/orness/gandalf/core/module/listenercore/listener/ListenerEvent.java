package com.orness.gandalf.core.module.listenercore.listener;


import com.orness.gandalf.core.module.listenercore.properties.ListenerProperties;
import com.orness.gandalf.core.module.zeromqcore.event.listener.ThreadListenerEventZeroMQ;
import org.zeromq.ZMsg;

public class ListenerEvent {

    private ListenerProperties listenerProperties;
    private ThreadListenerEventZeroMQ threadListenerEventZeroMQ;

    public ListenerEvent(ListenerProperties listenerProperties) {
        this.listenerProperties = listenerProperties;
        this.threadListenerEventZeroMQ = new ThreadListenerEventZeroMQ(this.listenerProperties.getConnectorName(), this.listenerProperties.getListenerEventBackEndConnection());
    }

    public ZMsg getEventSync() {
        return this.threadListenerEventZeroMQ.getEventSync();
    }

    public void startEventAsync() {
        this.threadListenerEventZeroMQ.start();
    }

    public void stopEventAsync() {
        this.threadListenerEventZeroMQ.stop();
    }

    public ZMsg getEventAsync() {
        return this.threadListenerEventZeroMQ.getEventAsync();
    }
}
