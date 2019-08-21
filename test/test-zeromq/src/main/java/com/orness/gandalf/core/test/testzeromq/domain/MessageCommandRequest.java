package com.orness.gandalf.core.test.testzeromq.domain;

import org.zeromq.ZFrame;
import org.zeromq.ZMsg;

public class MessageCommandRequest {

    private ZMsg request;
    private ZFrame sender;
    private ZFrame service;
    private ZFrame typeCommand;
    private ZFrame command;

    public ZMsg getRequest() {
        return request;
    }

    public void setRequest(ZMsg request) {
        this.request = request;
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

    public MessageCommandRequest(ZMsg request) {
        this.request = request;
        this.sender = request.pop();
        this.service = request.pop();
        this.typeCommand = request.pop();
        this.command = request.pop();
    }

    public MessageCommandRequest(ZFrame sender, ZFrame service, ZFrame typeCommand, ZFrame command) {
        this.sender = sender;
        this.service = service;
        this.typeCommand = typeCommand;
        this.command = command;
    }
}
