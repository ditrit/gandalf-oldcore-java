package com.orness.gandalf.core.module.connectormodule.gandalf.communication.event;

import com.orness.gandalf.core.module.zeromqmodule.command.client.ClientZeroMQ;

//TODO
public class ClientGandalfEvent extends ClientZeroMQ {

    public ClientGandalfEvent(String connection) {
        super(connection);
    }
}
