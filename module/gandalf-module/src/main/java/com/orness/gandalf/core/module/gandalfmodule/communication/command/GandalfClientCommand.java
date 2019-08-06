package com.orness.gandalf.core.module.gandalfmodule.communication.command;

import com.orness.gandalf.core.module.gandalfmodule.properties.properties.GandalfProperties;
import com.orness.gandalf.core.module.zeromqmodule.command.client.ClientZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.CommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

import static com.orness.gandalf.core.module.gandalfmodule.constant.GandalfConstant.*;

public class GandalfClientCommand extends ClientZeroMQ {

    private GandalfProperties gandalfProperties;

    @Autowired
    public GandalfClientCommand(GandalfProperties gandalfProperties) {
        super();
        this.gandalfProperties = gandalfProperties;
        this.connect(gandalfProperties.getClient());
    }

    public MessageCommandZeroMQ sendStartCommand() {
        CommandZeroMQ.sendCommand(this.client, this.identity, "" , COMMAND_TYPE_GANDALF, COMMAND_START);
        return CommandZeroMQ.receiveCommand(this.client);
    }

    public MessageCommandZeroMQ sendStopCommand() {
        CommandZeroMQ.sendCommand(this.client, this.identity, "" , COMMAND_TYPE_GANDALF, COMMAND_STOP);
        return CommandZeroMQ.receiveCommand(this.client);
    }

    public MessageCommandZeroMQ sendSubscriberCommand() {
        CommandZeroMQ.sendCommand(this.client, this.identity, "" , COMMAND_TYPE_GANDALF, COMMAND_SUBSCRIBE);
        return CommandZeroMQ.receiveCommand(this.client);
    }

    public MessageCommandZeroMQ sendUnsubscribeCommand() {
        CommandZeroMQ.sendCommand(this.client, this.identity, "" , COMMAND_TYPE_GANDALF, COMMAND_UNSUBSCRIBE);
        return CommandZeroMQ.receiveCommand(this.client);
    }
}
