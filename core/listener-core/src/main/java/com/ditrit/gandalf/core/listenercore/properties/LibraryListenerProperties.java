package com.ditrit.gandalf.core.listenercore.properties;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.ditrit.gandalf.core.listenercore.constant.LibraryListenerConstant.*;

@Configuration
public class LibraryListenerProperties {

    private RestTemplate restTemplate;

    @Value("${connector.name}")
    private String connectorName;

    private List<String> listenerCommandBackEndConnections;
    private String listenerEventBackEndConnection;

    public LibraryListenerProperties() {
        this.restTemplate = new RestTemplate();
        this.initProperties();
    }

    private JsonObject restTemplateRequest(String clusterPropertiesName) {
        return this.restTemplate.getForObject(" http://localhost:8500/v1/catalog/service/"+clusterPropertiesName, JsonObject.class);
    }

    private void initProperties() {
        //CLIENT COMMAND FRONT
        JsonObject currentclusterPropertiesJsonObject = this.restTemplateRequest(GANDALF_CLUSTER_COMMAND_BACKEND);
        this.listenerCommandBackEndConnections = StreamSupport.stream(currentclusterPropertiesJsonObject.getAsJsonArray("res").spliterator(), false)
                .map(JsonObject.class::cast)
                .map(o -> concatAddressPort(o.get(GANDALF_CLUSTER_ADDRESS), o.get(GANDALF_CLUSTER_PORT)))
                .collect(Collectors.toList());

        //CLIENT EVENT FRONT
        currentclusterPropertiesJsonObject = this.restTemplateRequest(GANDALF_CLUSTER_EVENT_BACKEND).getAsJsonObject("res");
        this.listenerEventBackEndConnection = concatAddressPort(currentclusterPropertiesJsonObject.get(GANDALF_CLUSTER_ADDRESS), currentclusterPropertiesJsonObject.get(GANDALF_CLUSTER_PORT));
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

    public List<String> getListenerCommandBackEndConnections() {
        return listenerCommandBackEndConnections;
    }

    public void setListenerCommandBackEndConnections(List<String> listenerCommandBackEndConnections) {
        this.listenerCommandBackEndConnections = listenerCommandBackEndConnections;
    }

    public String getListenerEventBackEndConnection() {
        return listenerEventBackEndConnection;
    }

    public void setListenerEventBackEndConnection(String listenerEventBackEndConnection) {
        this.listenerEventBackEndConnection = listenerEventBackEndConnection;
    }
}
