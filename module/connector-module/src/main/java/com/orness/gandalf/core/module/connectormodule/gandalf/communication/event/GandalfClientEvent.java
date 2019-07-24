package com.orness.gandalf.core.module.connectormodule.gandalf.communication.event;

import com.orness.gandalf.core.module.zeromqmodule.command.client.ClientZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.CommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;

public class GandalfClientEvent extends ClientZeroMQ {

    public GandalfClientEvent(String connection) {
        super(connection);
    }

    public MessageCommandZeroMQ sendEventCommand(String typeEvent, String event) {
        CommandZeroMQ.sendCommand(this.client, this.identity, "" , typeEvent, event);
        return CommandZeroMQ.receiveCommand(this.client);
    }
}
