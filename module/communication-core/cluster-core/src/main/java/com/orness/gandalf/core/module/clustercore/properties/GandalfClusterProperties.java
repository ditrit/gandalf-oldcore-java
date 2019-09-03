package com.orness.gandalf.core.module.clustercore.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GandalfClusterProperties {

    @Value("${cluster.name}")
    private String clusterName;

    //BROKER//
    //FRONT
    @Value("${${cluster.name}.brokerFrontEndConnection}")
    private String brokerFrontEndConnection;
    //BACK
    @Value("${${cluster.name}.brokerBackEndConnection}")
    private String brokerBackEndConnection;
    //CAPTURE
    @Value("${${cluster.name}.brokerCaptureConnection}")
    private String brokerCaptureConnection;

    //PROXY//
    //FRONT
    @Value("${${cluster.name}.proxyFrontEndConnection}")
    private String proxyFrontEndConnection;
    //BACK
    @Value("${${cluster.name}.proxyBackEndConnection}")
    private String proxyBackEndConnection;
    //CAPTURE
    @Value("${${cluster.name}.proxyCaptureConnection}")
    private String proxyCaptureConnection;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

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
