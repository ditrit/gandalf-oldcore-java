package com.ditrit.gandalf.gandalfjava.core.listenercore.listener;

import com.ditrit.gandalf.gandalfjava.core.listenercore.properties.ListenerProperties;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.library.listener.RunnableListenerCommandZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value = "listener")
@Scope("singleton")
public class ListenerEvent extends RunnableListenerCommandZeroMQ {

    private ListenerProperties listenerProperties;

    @Autowired
    public ListenerEvent(ListenerProperties listenerProperties) {
        this.listenerProperties = listenerProperties;
        this.initRunnable(this.listenerProperties.getConnectorName(), this.listenerProperties.getListenerEventBackEndConnection());
    }
}
