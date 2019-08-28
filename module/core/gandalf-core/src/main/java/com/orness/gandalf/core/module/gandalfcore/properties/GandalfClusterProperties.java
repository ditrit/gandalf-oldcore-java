package com.orness.gandalf.core.module.gandalfcore.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="gandalf.cluster")
public class GandalfClusterProperties {

    //BROKER//
    //FRONT
    private String brokerFrontEndConnection;
    //BACK
    private String brokerBackEndConnection;
    //CAPTURE
    private String brokerCaptureConnection;

    //PROXY//
    //FRONT
    private String proxyFrontEndConnection;
    //BACK
    private String proxyBackEndConnection;
    //CAPTURE
    private String proxyCaptureConnection;

    public String getBrokerFrontEndConnection() {
        return brokerFrontEndConnection;
    }

    public void setBrokerFrontEndConnection(String brokerFrontEndConnection) {
        this.brokerFrontEndConnection = brokerFrontEndConnection;
    }

    public String getBrokerBackEndConnection() {
        return brokerBackEndConnection;
    }

    public void setBrokerBackEndConnection(String brokerBackEndConnection) {
        this.brokerBackEndConnection = brokerBackEndConnection;
    }

    public String getBrokerCaptureConnection() {
        return brokerCaptureConnection;
    }

    public void setBrokerCaptureConnection(String brokerCaptureConnection) {
        this.brokerCaptureConnection = brokerCaptureConnection;
    }

    public String getProxyFrontEndConnection() {
        return proxyFrontEndConnection;
    }

    public void setProxyFrontEndConnection(String proxyFrontEndConnection) {
        this.proxyFrontEndConnection = proxyFrontEndConnection;
    }

    public String getProxyBackEndConnection() {
        return proxyBackEndConnection;
    }

    public void setProxyBackEndConnection(String proxyBackEndConnection) {
        this.proxyBackEndConnection = proxyBackEndConnection;
    }

    public String getProxyCaptureConnection() {
        return proxyCaptureConnection;
    }

    public void setProxyCaptureConnection(String proxyCaptureConnection) {
        this.proxyCaptureConnection = proxyCaptureConnection;
    }
}
