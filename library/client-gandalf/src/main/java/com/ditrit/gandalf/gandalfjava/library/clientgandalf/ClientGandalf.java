package com.ditrit.gandalf.gandalfjava.library.clientgandalf;

import com.ditrit.gandalf.gandalfjava.core.clientcore.client.ClientCommand;
import com.ditrit.gandalf.gandalfjava.core.clientcore.client.ClientEvent;
import com.ditrit.gandalf.gandalfjava.core.listenercore.listener.ListenerCommand;
import com.ditrit.gandalf.gandalfjava.core.listenercore.listener.ListenerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages = {"com.ditrit.gandalf.gandalfjava.core.clientcore", "com.ditrit.gandalf.gandalfjava.library.listenercore"})
public class ClientGandalf {

    private ClientCommand clientCommand;
    private ClientEvent clientEvent;
    private ListenerCommand listenerCommand;
    private ListenerEvent listenerEvent;

    @Autowired
    public ClientGandalf(ClientCommand clientCommand, ClientEvent clientEvent, ListenerCommand listenerCommand, ListenerEvent listenerEvent) {
        this.clientCommand = clientCommand;
        this.clientEvent = clientEvent;
        this.listenerCommand = listenerCommand;
        this.listenerEvent = listenerEvent;
    }

    public ClientCommand getClientCommand() {
        return clientCommand;
    }

    public void setClientCommand(ClientCommand clientCommand) {
        this.clientCommand = clientCommand;
    }

    public ClientEvent getClientEvent() {
        return clientEvent;
    }

    public void setClientEvent(ClientEvent clientEvent) {
        this.clientEvent = clientEvent;
    }

    public ListenerCommand getListenerCommand() {
        return listenerCommand;
    }

    public void setListenerCommand(ListenerCommand listenerCommand) {
        this.listenerCommand = listenerCommand;
    }

    public ListenerEvent getListenerEvent() {
        return listenerEvent;
    }

    public void setListenerEvent(ListenerEvent listenerEvent) {
        this.listenerEvent = listenerEvent;
    }
}
