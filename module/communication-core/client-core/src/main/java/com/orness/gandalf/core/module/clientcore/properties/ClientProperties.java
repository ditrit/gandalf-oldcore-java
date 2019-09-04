package com.orness.gandalf.core.module.clientcore.properties;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.orness.gandalf.core.module.clientcore.constant.ClientConstant.*;

@Configuration
public class ClientProperties {

    private RestTemplate restTemplate;

    @Value("${connector.name}")
    private String connectorName;

    private List<String> clientCommandBackEndConnections;
    private String clientEventBackEndConnection;

    public ClientProperties() {
        this.restTemplate = new RestTemplate();
        this.initProperties();
    }

    private JsonObject restTemplateRequest(String clusterPropertiesName) {
        return this.restTemplate.getForObject(" http://localhost:8500/v1/catalog/service/"+clusterPropertiesName, JsonObject.class);
    }

    private void initProperties() {
        //CLIENT COMMAND FRONT
        JsonObject currentclusterPropertiesJsonObject = this.restTemplateRequest(GANDALF_CLUSTER_COMMAND_FRONTEND);
        this.clientCommandBackEndConnections = StreamSupport.stream(currentclusterPropertiesJsonObject.getAsJsonArray("res").spliterator(), false)
                .map(JsonObject.class::cast)
                .map(o -> concatAddressPort(o.get(GANDALF_CLUSTER_ADDRESS), o.get(GANDALF_CLUSTER_PORT)))
                .collect(Collectors.toList());

        //CLIENT EVENT FRONT
        currentclusterPropertiesJsonObject = this.restTemplateRequest(GANDALF_CLUSTER_EVENT_FRONTEND).getAsJsonObject("res");
        this.clientEventBackEndConnection = concatAddressPort(currentclusterPropertiesJsonObject.get(GANDALF_CLUSTER_ADDRESS), currentclusterPropertiesJsonObject.get(GANDALF_CLUSTER_PORT));
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

    public String getClientEventBackEndConnection() {
        return clientEventBackEndConnection;
    }

    public void setClientEventBackEndConnection(String clientEventBackEndConnection) {
        this.clientEventBackEndConnection = clientEventBackEndConnection;
    }

    public List<String> getClientCommandBackEndConnections() {
        return clientCommandBackEndConnections;
    }

    public void setClientCommandBackEndConnections(List<String> clientCommandBackEndConnections) {
        this.clientCommandBackEndConnections = clientCommandBackEndConnections;
    }
}
