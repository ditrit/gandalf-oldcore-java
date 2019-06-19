package com.orness.gandalf.core.service.webhookservice.manager;

import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebhookManager {

    private JsonParser jsonObject;

    @Autowired
    public WebhookManager() {
        this.jsonObject = new JsonParser();
    }


}
