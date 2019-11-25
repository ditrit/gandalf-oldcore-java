package com.ditrit.gandalf.gandalfjava.core.clustercore.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="instance")
public class ClusterProperties {

    private String name;

    //COMMAND//
    //FRONT
    private String commandFrontEndConnection;
    //BACK
    private String commandBackEndConnection;
    //CAPTURE
    private String commandCaptureConnection;

    //EVENT//
    //FRONT
    private String eventFrontEndConnection;
    //BACK
    private String eventBackEndConnection;
    //CAPTURE
    private String eventCaptureConnection;

    //SERVICE//
    //CLIENT//
    private String serviceClientConnection;
    //LISTENER
    private String serviceListenerConnection;

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

    public String getServiceClientConnection() {
        return serviceClientConnection;
    }

    public void setServiceClientConnection(String serviceClientConnection) {
        this.serviceClientConnection = serviceClientConnection;
    }

    public String getServiceListenerConnection() {
        return serviceListenerConnection;
    }

    public void setServiceListenerConnection(String serviceListenerConnection) {
        this.serviceListenerConnection = serviceListenerConnection;
    }
}
