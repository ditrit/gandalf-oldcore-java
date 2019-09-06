package com.orness.gandalf.core.module.listenercore;

import com.orness.gandalf.core.module.listenercore.listener.ListenerCommand;
import com.orness.gandalf.core.module.listenercore.listener.ListenerEvent;
import com.orness.gandalf.core.module.listenercore.properties.ListenerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "gandalfListener")
public class GandalfListener {

    private ListenerProperties listenerProperties;
    private ListenerCommand listenerCommand;
    private ListenerEvent listenerEvent;

    @Autowired
    public GandalfListener(ListenerProperties listenerProperties) {
        this.listenerProperties = listenerProperties;
        this.listenerCommand = new ListenerCommand(this.listenerProperties);
        this.listenerEvent = new ListenerEvent(this.listenerProperties);
    }

    public void getCommand() {
        this.listenerCommand.getCommand();
    }

    public void getEvent() {
        this.listenerEvent.getEvent();
    }
}
