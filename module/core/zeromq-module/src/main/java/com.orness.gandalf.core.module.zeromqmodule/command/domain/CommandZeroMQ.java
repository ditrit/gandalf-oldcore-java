package com.orness.gandalf.core.module.zeromqmodule.command.domain;

import org.zeromq.ZFrame;
import org.zeromq.ZMQ;

public class CommandZeroMQ {

    public static void sendCommand(ZMQ.Socket socket, String idSender, String idReceiver, String typeCommand, String command) {
        socket.sendMore(idSender);
        socket.sendMore(idReceiver);
        socket.sendMore(typeCommand);
        socket.send(command);
    }

    public static MessageCommandZeroMQ receiveCommand(ZMQ.Socket socket) {
        return new MessageCommandZeroMQ(socket);
    }

    public static void replyCommand(ZMQ.Socket socket, MessageCommandZeroMQ messageCommandZeroMQ) {
        messageCommandZeroMQ.getSender().send(socket, ZFrame.REUSE + ZFrame.MORE);
        messageCommandZeroMQ.getReceiver().send(socket, ZFrame.REUSE + ZFrame.MORE);
        messageCommandZeroMQ.getTypeCommand().send(socket, ZFrame.REUSE + ZFrame.MORE);
        messageCommandZeroMQ.getCommand().send(socket, ZFrame.REUSE);
    }


}
