package com.ditrit.gandalf.core.listenercore.listener;

import com.ditrit.gandalf.core.listenercore.properties.ListenerProperties;
import com.ditrit.gandalf.core.zeromqcore.library.listener.RunnableListenerCommandZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value = "listener")
@Scope("singleton")
public class ListenerCommand extends RunnableListenerCommandZeroMQ {

    private ListenerProperties listenerProperties;

    @Autowired
    public ListenerCommand(ListenerProperties listenerProperties) {
        this.listenerProperties = listenerProperties;
        this.initRunnable(this.listenerProperties.getConnectorName(), this.listenerProperties.getListenerCommandBackEndConnection());
    }
}
