package com.orness.gandalf.core.module.zeromqmodule.command.domain;

import org.zeromq.ZFrame;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public class ResponseCommandZeroMQ {

    private ZMsg message;
    private ZFrame idSender;
    private ZFrame idReceiver;
    private ZFrame typeCommand;
    private ZFrame command;

    private ZMsg getMessage() {
        return message;
    }

    private void setMessage(ZMsg message) {
        this.message = message;
    }

    public ZFrame getIdSender() {
        return idSender;
    }

    private void setIdSender(ZFrame idSender) {
        this.idSender = idSender;
    }

    public ZFrame getIdReceiver() {
        return idReceiver;
    }

    private void setIdReceiver(ZFrame idReceiver) {
        this.idReceiver = idReceiver;
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

    public ResponseCommandZeroMQ(ZMQ.Socket socket) {
        this.message = ZMsg.recvMsg(socket);
        this.idSender = message.pop();
        this.idReceiver = message.pop();
        this.typeCommand = message.pop();
        this.command = message.pop();
    }

    public ResponseCommandZeroMQ(ZMsg message, ZFrame idSender, ZFrame idReceiver, ZFrame typeCommand, ZFrame command) {
        this.message = message;
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.typeCommand = typeCommand;
        this.command = command;
    }
}
