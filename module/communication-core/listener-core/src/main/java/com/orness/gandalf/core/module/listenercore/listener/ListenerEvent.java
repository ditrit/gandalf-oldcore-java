package com.orness.gandalf.core.module.listenercore.listener;


import com.orness.gandalf.core.module.listenercore.properties.ListenerProperties;
import com.orness.gandalf.core.module.zeromqcore.event.listener.ListenerEventZeroMQ;

public class ListenerEvent extends ListenerEventZeroMQ {

    private ListenerProperties listenerProperties;

    public ListenerEvent(ListenerProperties listenerProperties) {
        super();
        this.listenerProperties = listenerProperties;
        this.init(this.listenerProperties.getConnectorName(), this.listenerProperties.getListenerEventBackEndConnection());
    }
}
