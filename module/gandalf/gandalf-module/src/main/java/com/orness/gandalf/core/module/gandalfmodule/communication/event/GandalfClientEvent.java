package com.orness.gandalf.core.module.gandalfmodule.communication.event;

import com.orness.gandalf.core.module.zeromqmodule.command.domain.CommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.client.ClientEventZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

public class GandalfClientEvent extends ClientEventZeroMQ {

    @Autowired
    public GandalfClientEvent(String  connection) {
        super();
        this.bind(connection);
    }

    public MessageCommandZeroMQ sendEventCommand(String typeEvent, String event) {
        CommandZeroMQ.sendCommand(this.client, this.identity, "" , typeEvent, event);
        return CommandZeroMQ.receiveCommand(this.client);
    }
}
