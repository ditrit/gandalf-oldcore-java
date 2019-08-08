package com.orness.gandalf.core.module.gandalfmodule.communication.event;

import com.orness.gandalf.core.module.gandalfmodule.properties.GandalfProperties;
import com.orness.gandalf.core.module.zeromqmodule.command.client.ClientZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.CommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GandalfClientEvent extends ClientZeroMQ {

    private GandalfProperties gandalfProperties;

    @Autowired
    public GandalfClientEvent(GandalfProperties gandalfProperties) {
        super();
        this.gandalfProperties = gandalfProperties;
        this.connect(gandalfProperties.getWorkerEvent());
    }

    public MessageCommandZeroMQ sendEventCommand(String typeEvent, String event) {
        CommandZeroMQ.sendCommand(this.client, this.identity, "" , typeEvent, event);
        return CommandZeroMQ.receiveCommand(this.client);
    }
}
