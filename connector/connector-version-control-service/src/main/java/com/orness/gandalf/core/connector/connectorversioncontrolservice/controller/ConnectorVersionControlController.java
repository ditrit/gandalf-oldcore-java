package com.orness.gandalf.core.connector.connectorversioncontrolservice.controller;

import com.orness.gandalf.core.connector.connectorversioncontrolservice.domain.CustomMergeRequest;
import com.orness.gandalf.core.connector.connectorversioncontrolservice.manager.ConnectorVersionControlManager;
import com.orness.gandalf.core.library.zeromqjavaclient.ZeroMQJavaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/version-control")
public class ConnectorVersionControlController {

    @Value("${gandalf.communication.client}")
    private String connectionWorker;
    @Value("${gandalf.communication.subscriber}")
    private String connectionSubscriber;
    @Value("${gandalf.webhook.topic}")
    private String topicWebhook;
    @Value("${gandalf.database.topic}")
    private String topicDatabase;

    private ConnectorVersionControlManager connectorVersionControlManager;

    @Autowired
    public ConnectorVersionControlController(ConnectorVersionControlManager connectorVersionControlManager) {
        this.connectorVersionControlManager = connectorVersionControlManager;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/merge")
    public void mergeRequestEvents(@RequestBody String mergeRequest) throws IOException {
        System.out.println(mergeRequest);
        CustomMergeRequest customMergeRequest = connectorVersionControlManager.parseEventMergeRequest(mergeRequest);
        System.out.println("TRIGGER");
        System.out.println(customMergeRequest);
        if(connectorVersionControlManager.validWebhookMergeRequest(customMergeRequest)) {
            System.out.println("VALID");
            ZeroMQJavaClient zeroMQJavaClient = new ZeroMQJavaClient(connectionWorker, connectionSubscriber);
            zeroMQJavaClient.sendMessageTopic(topicWebhook, customMergeRequest.getProjectUrl());
        }
    }
}