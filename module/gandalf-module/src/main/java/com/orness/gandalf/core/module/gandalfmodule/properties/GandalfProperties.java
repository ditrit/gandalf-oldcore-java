package com.orness.gandalf.core.module.gandalfmodule.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="gandalf")
public class GandalfProperties {

    private String clientBroker;
    private String workerBroker;
    private String publisherProxy;
    private String subscriberProxy;
    private String client;
    private String worker;
    private String publisher;
    private String subscriber;

    public String getClientBroker() {
        return clientBroker;
    }

    public void setClientBroker(String clientBroker) {
        this.clientBroker = clientBroker;
    }

    public String getWorkerBroker() {
        return workerBroker;
    }

    public void setWorkerBroker(String workerBroker) {
        this.workerBroker = workerBroker;
    }

    public String getPublisherProxy() {
        return publisherProxy;
    }

    public void setPublisherProxy(String publisherProxy) {
        this.publisherProxy = publisherProxy;
    }

    public String getSubscriberProxy() {
        return subscriberProxy;
    }

    public void setSubscriberProxy(String subscriberProxy) {
        this.subscriberProxy = subscriberProxy;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }
}
