package com.ditrit.gandalf.gandalfjava.core.zeromqcore.command.domain;

import org.zeromq.ZMsg;

public class MessageCommand {

    private ZMsg message;
    private String uuid;
    String sourceConnector;
    String sourceServiceClass;
    String targetConnector;
    String targetServiceClass;
    private String command;
    private String timeout;
    private String timestamp;
    private String payload;

    public ZMsg getMessage() {
        return message;
    }

    public void setMessage(ZMsg message) {
        this.message = message;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSourceConnector() {
        return sourceConnector;
    }

    public void setSourceConnector(String sourceConnector) {
        this.sourceConnector = sourceConnector;
    }

    public String getSourceServiceClass() {
        return sourceServiceClass;
    }

    public void setSourceServiceClass(String sourceServiceClass) {
        this.sourceServiceClass = sourceServiceClass;
    }

    public String getTargetConnector() {
        return targetConnector;
    }

    public void setTargetConnector(String targetConnector) {
        this.targetConnector = targetConnector;
    }

    public String getTargetServiceClass() {
        return targetServiceClass;
    }

    public void setTargetServiceClass(String targetServiceClass) {
        this.targetServiceClass = targetServiceClass;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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
        this.sourceConnector = this.message.popString();
        this.sourceServiceClass = this.message.popString();
        this.targetConnector = this.message.popString();
        this.targetServiceClass = this.message.popString();
        this.command = this.message.popString();
        this.timeout = this.message.popString();
        this.timestamp = this.message.popString();
        this.payload = this.message.popString();
    }
}
