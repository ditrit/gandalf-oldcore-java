/*package com.ditrit.gandalf.core.clientcore.library.properties;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.ditrit.gandalf.core.clientcore.library.constant.LibraryClientConstant.*;

@Configuration
public class LibraryClientProperties {
    
    private RestTemplate restTemplate;
    private Gson mapper;

    @Value("${connector.name}")
    private String connectorName;


    @Value("tcp://127.0.0.1:9020")
    private String connectorCommandBackEndSendConnection;
    @Value("tcp://127.0.0.1:9022")
    private String connectorEventBackEndSendConnection;


    private List<String> clientCommandBackEndConnections;
    private String clientEventBackEndConnection;

    public LibraryClientProperties() {
        this.restTemplate = new RestTemplate();
        this.mapper = new Gson();
        //this.initProperties();
    }

    private JsonArray restTemplateRequest(String clusterPropertiesName) {
        return mapper.fromJson(this.restTemplate.getForObject("http://localhost:8500/v1/catalog/service/"+clusterPropertiesName, String.class), JsonArray.class);
    }

   private void initProperties() {
        //CONNECTEUR COMMAND FRONT
        JsonArray currentclusterPropertiesJsonArray = this.restTemplateRequest(GANDALF_CLUSTER_COMMAND_FRONTEND);
        this.clientCommandBackEndConnections = StreamSupport.stream(currentclusterPropertiesJsonArray.spliterator(), false)
                .map(JsonObject.class::cast)
                .map(o -> concatAddressPort(o.get(GANDALF_CLUSTER_ADDRESS), o.get(GANDALF_CLUSTER_PORT)))
                .collect(Collectors.toList());

        //CONNECTEUR EVENT FRONT
        currentclusterPropertiesJsonArray = this.restTemplateRequest(GANDALF_CLUSTER_EVENT_FRONTEND);
        JsonObject currentclusterPropertiesJsonObject = currentclusterPropertiesJsonArray.get(0).getAsJsonObject();
        this.clientEventBackEndConnection = concatAddressPort(currentclusterPropertiesJsonObject.get(GANDALF_CLUSTER_ADDRESS), currentclusterPropertiesJsonObject.get(GANDALF_CLUSTER_PORT));
    }

    private String concatAddressPort(JsonElement address, JsonElement port) {
        return new StringBuilder("tcp://").append(address.getAsString()).append(":").append(port.getAsString()).toString();
    }

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public String getConnectorCommandBackEndSendConnection() {
        return connectorCommandBackEndSendConnection;
    }

    public void setConnectorCommandBackEndSendConnection(String connectorCommandBackEndSendConnection) {
        this.connectorCommandBackEndSendConnection = connectorCommandBackEndSendConnection;
    }

    public String getConnectorEventBackEndSendConnection() {
        return connectorEventBackEndSendConnection;
    }

    public void setConnectorEventBackEndSendConnection(String connectorEventBackEndSendConnection) {
        this.connectorEventBackEndSendConnection = connectorEventBackEndSendConnection;
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
}*/

