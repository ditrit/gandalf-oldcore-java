package com.orness.gandalf.core.connector.connectorversioncontrolservice.other.manager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orness.gandalf.core.module.webhookmodule.domain.CustomMergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConnectorVersionControlManager {

    private ObjectMapper objectMapper;

    @Autowired
    public ConnectorVersionControlManager() {
        this.objectMapper = new ObjectMapper();
    }


    public CustomMergeRequest parseEventMergeRequest(String mergeRequestBody) throws IOException {
        System.out.println(mergeRequestBody);
        JsonNode rootNode = objectMapper.readTree(mergeRequestBody);
        CustomMergeRequest customMergeRequest = new CustomMergeRequest(rootNode.path("project").path("name").textValue(), rootNode.path("project").path("http_url").textValue(), rootNode.path("project").path("ssh_url").textValue(), rootNode.path("project").path("git_http_url").textValue(), rootNode.path("project").path("git_ssh_url").textValue(), rootNode.path("object_attributes").path("source_branch").textValue(), rootNode.path("object_attributes").path("target_branch").textValue());
        System.out.println(customMergeRequest);
        return customMergeRequest;
    }

    public boolean validWebhookMergeRequest(CustomMergeRequest customMergeRequest) {
        boolean valid = false;
        if(customMergeRequest.getMergeTarget().equals("master")) {
            valid = true;
        }
        return valid;
    }
}
