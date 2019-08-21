package com.orness.gandalf.core.test.testzeromq.domain;

import org.zeromq.ZFrame;
import org.zeromq.ZMsg;

public class MessageCommandResponse {

    private ZMsg response;
    private ZFrame sender;
    private ZFrame service;
    private ZFrame receiver;
    private ZFrame typeCommand;
    private ZFrame command;

    public ZMsg getResponse() {
        return response;
    }

    public void setResponse(ZMsg response) {
        this.response = response;
    }

    public ZFrame getSender() {
        return sender;
    }

    public void setSender(ZFrame sender) {
        this.sender = sender;
    }

    public ZFrame getService() {
        return service;
    }

    public void setService(ZFrame service) {
        this.service = service;
    }

    public ZFrame getReceiver() {
        return receiver;
    }

    public void setReceiver(ZFrame receiver) {
        this.receiver = receiver;
    }

    public ZFrame getTypeCommand() {
        return typeCommand;
    }

    public void setTypeCommand(ZFrame typeCommand) {
        this.typeCommand = typeCommand;
    }

    public ZFrame getCommand() {
        return command;
    }

    public void setCommand(ZFrame command) {
        this.command = command;
    }

    public MessageCommandResponse(ZMsg response) {
        this.response = response;
        this.sender = response.pop();
        this.service = response.pop();
        this.receiver = response.pop();
        this.typeCommand = response.pop();
        this.command = response.pop();
    }

    public MessageCommandResponse(ZFrame sender, ZFrame service, ZFrame receiver, ZFrame typeCommand, ZFrame command) {
        this.sender = sender;
        this.service = service;
        this.receiver = receiver;
        this.typeCommand = typeCommand;
        this.command = command;
    }
}
