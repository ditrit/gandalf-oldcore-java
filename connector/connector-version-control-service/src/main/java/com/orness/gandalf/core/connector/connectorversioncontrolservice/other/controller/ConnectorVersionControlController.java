package com.orness.gandalf.core.connector.connectorversioncontrolservice.other.controller;

import com.orness.gandalf.core.connector.connectorversioncontrolservice.other.manager.ConnectorVersionControlManager;
import com.orness.gandalf.core.library.zeromqjavaclient.ZeroMQJavaClient;
import com.orness.gandalf.core.module.webhookmodule.domain.CustomMergeRequest;
import com.orness.gandalf.core.module.webhookmodule.parser.CustomMergeRequestParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ConnectorVersionControlController {

    @Value("${gandalf.communication.client}")
    private String connectionWorker;
    @Value("${gandalf.communication.subscriber}")
    private String connectionSubscriber;
    @Value("${gandalf.webhook.topic}")
    private String topicWebhook;
//    @Value("${gandalf.database.topic}")
//    private String topicDatabase;

    private ConnectorVersionControlManager connectorVersionControlManager;

    @Autowired
    public ConnectorVersionControlController(ConnectorVersionControlManager connectorVersionControlManager) {
        this.connectorVersionControlManager = connectorVersionControlManager;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/version-control/merge")
    public void mergeRequestEvents(@RequestBody String mergeRequest) throws IOException {

        CustomMergeRequest customMergeRequest = connectorVersionControlManager.parseEventMergeRequest(mergeRequest);

        if(connectorVersionControlManager.validWebhookMergeRequest(customMergeRequest)) {
            ZeroMQJavaClient zeroMQJavaClient = new ZeroMQJavaClient(connectionWorker, connectionSubscriber);
            zeroMQJavaClient.sendMessageTopic(topicWebhook, CustomMergeRequestParser.parseObjectToString(customMergeRequest));
        }
    }
}