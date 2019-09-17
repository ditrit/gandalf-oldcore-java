package com.ditrit.gandalf.tests.testgandalf1.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="gandalf")
public class GandalfProperties {

    private String connectorName;
    private String clientBackEndConnection1;
    private String clientBackEndConnection2;
    private String clientBackEndConnection3;
    private String publisherBackEndConnection;
    private String routingWorkerFrontEndConnection1;
    private String routingWorkerFrontEndConnection2;
    private String routingWorkerFrontEndConnection3;
    private String routingWorkerBackEndConnection;
    private String routingSubscriberFrontEndConnection;
    private String routingSubscriberBackEndConnection;
    private String captureBrokerBackEndConnection1;
    private String captureBrokerBackEndConnection2;
    private String captureBrokerBackEndConnection3;
    private String captureProxyBackEndConnection1;
    private String captureProxyBackEndConnection2;

    public String getRoutingWorkerBackEndConnection() {
        return routingWorkerBackEndConnection;
    }

    public void setRoutingWorkerBackEndConnection(String routingWorkerBackEndConnection) {
        this.routingWorkerBackEndConnection = routingWorkerBackEndConnection;
    }

    private String captureProxyBackEndConnection3;
    private List<String> topics;

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public String getClientBackEndConnection1() {
        return clientBackEndConnection1;
    }

    public void setClientBackEndConnection1(String clientBackEndConnection1) {
        this.clientBackEndConnection1 = clientBackEndConnection1;
    }

    public String getClientBackEndConnection2() {
        return clientBackEndConnection2;
    }

    public void setClientBackEndConnection2(String clientBackEndConnection2) {
        this.clientBackEndConnection2 = clientBackEndConnection2;
    }

    public String getClientBackEndConnection3() {
        return clientBackEndConnection3;
    }

    public void setClientBackEndConnection3(String clientBackEndConnection3) {
        this.clientBackEndConnection3 = clientBackEndConnection3;
    }

    public String getPublisherBackEndConnection() {
        return publisherBackEndConnection;
    }

    public void setPublisherBackEndConnection(String publisherBackEndConnection) {
        this.publisherBackEndConnection = publisherBackEndConnection;
    }

    public String getRoutingWorkerFrontEndConnection1() {
        return routingWorkerFrontEndConnection1;
    }

    public void setRoutingWorkerFrontEndConnection1(String routingWorkerFrontEndConnection1) {
        this.routingWorkerFrontEndConnection1 = routingWorkerFrontEndConnection1;
    }

    public String getRoutingWorkerFrontEndConnection2() {
        return routingWorkerFrontEndConnection2;
    }

    public void setRoutingWorkerFrontEndConnection2(String routingWorkerFrontEndConnection2) {
        this.routingWorkerFrontEndConnection2 = routingWorkerFrontEndConnection2;
    }

    public String getRoutingWorkerFrontEndConnection3() {
        return routingWorkerFrontEndConnection3;
    }

    public void setRoutingWorkerFrontEndConnection3(String routingWorkerFrontEndConnection3) {
        this.routingWorkerFrontEndConnection3 = routingWorkerFrontEndConnection3;
    }

    public String getRoutingSubscriberBackEndConnection() {
        return routingSubscriberBackEndConnection;
    }

    public void setRoutingSubscriberBackEndConnection(String routingSubscriberBackEndConnection) {
        this.routingSubscriberBackEndConnection = routingSubscriberBackEndConnection;
    }

    public String getRoutingSubscriberFrontEndConnection() {
        return routingSubscriberFrontEndConnection;
    }

    public void setRoutingSubscriberFrontEndConnection(String routingSubscriberFrontEndConnection) {
        this.routingSubscriberFrontEndConnection = routingSubscriberFrontEndConnection;
    }

    public String getCaptureBrokerBackEndConnection1() {
        return captureBrokerBackEndConnection1;
    }

    public void setCaptureBrokerBackEndConnection1(String captureBrokerBackEndConnection1) {
        this.captureBrokerBackEndConnection1 = captureBrokerBackEndConnection1;
    }

    public String getCaptureBrokerBackEndConnection2() {
        return captureBrokerBackEndConnection2;
    }

    public void setCaptureBrokerBackEndConnection2(String captureBrokerBackEndConnection2) {
        this.captureBrokerBackEndConnection2 = captureBrokerBackEndConnection2;
    }

    public String getCaptureBrokerBackEndConnection3() {
        return captureBrokerBackEndConnection3;
    }

    public void setCaptureBrokerBackEndConnection3(String captureBrokerBackEndConnection3) {
        this.captureBrokerBackEndConnection3 = captureBrokerBackEndConnection3;
    }

    public String getCaptureProxyBackEndConnection1() {
        return captureProxyBackEndConnection1;
    }

    public void setCaptureProxyBackEndConnection1(String captureProxyBackEndConnection1) {
        this.captureProxyBackEndConnection1 = captureProxyBackEndConnection1;
    }

    public String getCaptureProxyBackEndConnection2() {
        return captureProxyBackEndConnection2;
    }

    public void setCaptureProxyBackEndConnection2(String captureProxyBackEndConnection2) {
        this.captureProxyBackEndConnection2 = captureProxyBackEndConnection2;
    }

    public String getCaptureProxyBackEndConnection3() {
        return captureProxyBackEndConnection3;
    }

    public void setCaptureProxyBackEndConnection3(String captureProxyBackEndConnection3) {
        this.captureProxyBackEndConnection3 = captureProxyBackEndConnection3;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
