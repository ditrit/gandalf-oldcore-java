package com.orness.gandalf.core.module.zeromqmodule.command.domain;

import org.zeromq.ZMQ;

public class CommandZeroMQ {

    public static void sendCommand(ZMQ.Socket socket, String idSender, String idReceiver, String typeCommand, String command) {
        socket.sendMore(idSender);
        socket.sendMore(idReceiver);
        socket.sendMore(typeCommand);
        socket.send(command);
    }

    public static ResponseCommandZeroMQ receiveCommand(ZMQ.Socket socket) {
        return new ResponseCommandZeroMQ(socket);
    }
}
