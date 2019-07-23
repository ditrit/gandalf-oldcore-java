package com.orness.gandalf.core.module.zeromqmodule.command.domain;

import org.zeromq.ZFrame;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public class MessageCommandZeroMQ {

    private ZMsg message;
    private ZFrame sender;
    private ZFrame receiver;
    private ZFrame typeCommand;
    private ZFrame command;

    private ZMsg getMessage() {
        return message;
    }

    private void setMessage(ZMsg message) {
        this.message = message;
    }

    public ZFrame getSender() {
        return sender;
    }

    private void setSender(ZFrame sender) {
        this.sender = sender;
    }

    public ZFrame getReceiver() {
        return receiver;
    }

    private void setReceiver(ZFrame receiver) {
        this.receiver = receiver;
    }

    public ZFrame getTypeCommand() {
        return typeCommand;
    }

    private void setTypeCommand(ZFrame typeCommand) {
        this.typeCommand = typeCommand;
    }

    public ZFrame getCommand() {
        return command;
    }

    private void setCommand(ZFrame command) {
        this.command = command;
    }

    public MessageCommandZeroMQ(ZMQ.Socket socket) {
        this.message = ZMsg.recvMsg(socket);
        this.sender = message.pop();
        this.receiver = message.pop();
        this.typeCommand = message.pop();
        this.command = message.pop();
    }

    public MessageCommandZeroMQ(ZMsg message, ZFrame sender, ZFrame receiver, ZFrame typeCommand, ZFrame command) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.typeCommand = typeCommand;
        this.command = command;
    }
}
