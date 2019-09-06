package com.orness.gandalf.core.module.listenercore.listener;


import com.orness.gandalf.core.module.listenercore.properties.ListenerProperties;
import com.orness.gandalf.core.module.zeromqcore.command.listener.ListenerCommandZeroMQ;

public class ListenerCommand extends ListenerCommandZeroMQ {

    private ListenerProperties listenerProperties;

    public ListenerCommand(ListenerProperties listenerProperties) {
        super();
        this.listenerProperties = listenerProperties;
        this.init(this.listenerProperties.getConnectorName(), this.listenerProperties.getListenerCommandBackEndConnections());
    }
}
