package com.orness.gandalf.core.connector.connectorversioncontrolservice.controller;

import com.orness.gandalf.core.connector.connectorversioncontrolservice.manager.ConnectorVersionControlManager;
import com.orness.gandalf.core.library.zeromqjavaclient.ZeroMQJavaClient;
import org.gitlab4j.api.webhook.EventMergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public void mergeRequestEvents(@RequestBody String mergeRequest) {
        EventMergeRequest eventMergeRequest = connectorVersionControlManager.parseEventMergeRequest(mergeRequest);
        if(connectorVersionControlManager.validWebhookMergeRequest(eventMergeRequest)) {
            ZeroMQJavaClient zeroMQJavaClient = new ZeroMQJavaClient(connectionWorker, connectionSubscriber);
            zeroMQJavaClient.sendMessageTopic(topicWebhook, eventMergeRequest.getTarget().getGitHttpUrl());
        }
    }
}