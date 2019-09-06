package com.orness.gandalf.core.module.clustercore.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="instance")
public class GandalfClusterProperties {

    private String name;

    //BROKER//
    //FRONT
    private String commandFrontEndConnection;
    //BACK
    private String commandBackEndConnection;
    //CAPTURE
    private String commandCaptureConnection;

    //PROXY//
    //FRONT
    private String eventFrontEndConnection;
    //BACK
    private String eventBackEndConnection;
    //CAPTURE
    private String eventCaptureConnection;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommandFrontEndConnection() {
        return commandFrontEndConnection;
    }

    public void setCommandFrontEndConnection(String commandFrontEndConnection) {
        this.commandFrontEndConnection = commandFrontEndConnection;
    }

    public String getCommandBackEndConnection() {
        return commandBackEndConnection;
    }

    public void setCommandBackEndConnection(String commandBackEndConnection) {
        this.commandBackEndConnection = commandBackEndConnection;
    }

    public String getCommandCaptureConnection() {
        return commandCaptureConnection;
    }

    public void setCommandCaptureConnection(String commandCaptureConnection) {
        this.commandCaptureConnection = commandCaptureConnection;
    }

    public String getEventFrontEndConnection() {
        return eventFrontEndConnection;
    }

    public void setEventFrontEndConnection(String eventFrontEndConnection) {
        this.eventFrontEndConnection = eventFrontEndConnection;
    }

    public String getEventBackEndConnection() {
        return eventBackEndConnection;
    }

    public void setEventBackEndConnection(String eventBackEndConnection) {
        this.eventBackEndConnection = eventBackEndConnection;
    }

    public String getEventCaptureConnection() {
        return eventCaptureConnection;
    }

    public void setEventCaptureConnection(String eventCaptureConnection) {
        this.eventCaptureConnection = eventCaptureConnection;
    }
}
