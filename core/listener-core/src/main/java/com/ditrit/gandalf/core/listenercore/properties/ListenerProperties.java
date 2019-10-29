package com.ditrit.gandalf.core.listenercore.properties;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ListenerProperties {

    private RestTemplate restTemplate;

    @Value("${connector.name}")
    private String connectorName;

    private String listenerCommandBackEndConnection;
    private String listenerEventBackEndConnection;

    public ListenerProperties() {
        this.restTemplate = new RestTemplate();
    }

    private JsonObject restTemplateRequest(String clusterPropertiesName) {
        return this.restTemplate.getForObject(" http://localhost:8500/v1/catalog/service/"+clusterPropertiesName, JsonObject.class);
    }

    private String concatAddressPort(JsonElement address, JsonElement port) {
        return new StringBuilder(address.getAsString()).append(":").append(port.getAsString()).toString();
    }

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public String getListenerCommandBackEndConnection() {
        return listenerCommandBackEndConnection;
    }

    public void setListenerCommandBackEndConnection(String listenerCommandBackEndConnection) {
        this.listenerCommandBackEndConnection = listenerCommandBackEndConnection;
    }

    public String getListenerEventBackEndConnection() {
        return listenerEventBackEndConnection;
    }

    public void setListenerEventBackEndConnection(String listenerEventBackEndConnection) {
        this.listenerEventBackEndConnection = listenerEventBackEndConnection;
    }
}
