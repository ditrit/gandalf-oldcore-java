package com.orness.gandalf.core.module.zeromqcore.command.domain;

import org.zeromq.ZMsg;

public class MessageCommand {

    private ZMsg message;
    private String client;
    private String uuid;
    private String connector;
    private String serviceClass;
    private String command;
    private String payload;
    private String createdAt;
    private String timeout;

    public ZMsg getMessage() {
        return message;
    }

    public void setMessage(ZMsg message) {
        this.message = message;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public MessageCommand(ZMsg command) {
        this.message = command.duplicate();
        this.uuid = this.message.popString();
        this.client = this.message.popString();
        this.connector = this.message.popString();
        this.serviceClass = this.message.popString();
        this.command = this.message.popString();
        this.payload = this.message.popString();
    }
}
