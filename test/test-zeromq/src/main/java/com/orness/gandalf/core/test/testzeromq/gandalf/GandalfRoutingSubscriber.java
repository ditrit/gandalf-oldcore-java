package com.orness.gandalf.core.test.testzeromq.gandalf;

import com.orness.gandalf.core.test.testzeromq.event.RunnableRoutingSubscriber;


public class GandalfRoutingSubscriber extends RunnableRoutingSubscriber {

    private String frontEndsubScriberConnection;
    private String backEndSubscriberConnection;

    public GandalfRoutingSubscriber(String identity, String frontEndsubScriberConnection, String backEndSubscriberConnection, String topic) {
        super();
        this.frontEndsubScriberConnection = frontEndsubScriberConnection;
        this.backEndSubscriberConnection = backEndSubscriberConnection;
        this.identity = identity;
        //SUBS
        //TODO IDENTITY
        this.initRunnable(this.identity, this.frontEndsubScriberConnection, this.backEndSubscriberConnection, topic);
    }
}