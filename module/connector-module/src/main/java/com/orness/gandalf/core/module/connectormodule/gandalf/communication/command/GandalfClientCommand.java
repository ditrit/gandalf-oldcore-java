package com.orness.gandalf.core.module.connectormodule.gandalf.communication.command;

import com.orness.gandalf.core.module.zeromqmodule.command.client.ClientZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.CommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;

import static com.orness.gandalf.core.module.constantmodule.communication.CommunicationConstant.*;

public class GandalfClientCommand extends ClientZeroMQ {

    public GandalfClientCommand(String connection) {
        super(connection);
    }

    public MessageCommandZeroMQ sendStartCommand() {
        CommandZeroMQ.sendCommand(client, identity, "" , COMMAND_TYPE_GANDALF, COMMAND_START);
        return CommandZeroMQ.receiveCommand(client);
    }

    public MessageCommandZeroMQ sendStopCommand() {
        CommandZeroMQ.sendCommand(client, identity, "" , COMMAND_TYPE_GANDALF, COMMAND_STOP);
        return CommandZeroMQ.receiveCommand(client);
    }

    public MessageCommandZeroMQ sendSubscriberCommand() {
        CommandZeroMQ.sendCommand(client, identity, "" , COMMAND_TYPE_GANDALF, COMMAND_SUBSCRIBE);
        return CommandZeroMQ.receiveCommand(client);
    }

    public MessageCommandZeroMQ sendUnsubscribeCommand() {
        CommandZeroMQ.sendCommand(client, identity, "" , COMMAND_TYPE_GANDALF, COMMAND_UNSUBSCRIBE);
        return CommandZeroMQ.receiveCommand(client);
    }
}
