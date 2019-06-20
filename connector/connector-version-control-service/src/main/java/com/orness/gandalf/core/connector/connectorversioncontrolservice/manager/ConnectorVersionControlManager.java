package com.orness.gandalf.core.connector.connectorversioncontrolservice.manager;

import com.google.gson.Gson;
import org.gitlab4j.api.webhook.EventMergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectorVersionControlManager {

    private Gson mapper;

    @Autowired
    public ConnectorVersionControlManager() {
        this.mapper = new Gson();
    }


    public EventMergeRequest parseEventMergeRequest(String mergeRequestBody) {
        return mapper.fromJson(mergeRequestBody, EventMergeRequest.class);
    }

    public boolean validWebhookMergeRequest(EventMergeRequest eventMergeRequest) {
        boolean valid = false;
        if(eventMergeRequest.getTargetBranch().equals("master")) {
            valid = true;
        }
        return valid;
    }
}
