package com.orness.gandalf.core.module.webhookmodule.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orness.gandalf.core.module.webhookmodule.domain.CustomMergeRequest;

import java.io.IOException;

public class CustomMergeRequestParser {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static CustomMergeRequest parseStringToObject(String json) {
        CustomMergeRequest customMergeRequest = null;
        try {
            customMergeRequest = objectMapper.readValue(json, CustomMergeRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customMergeRequest;
    }

    public static String parseObjectToString(CustomMergeRequest customMergeRequest) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(customMergeRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
