package com.orness.gandalf.core.module.artifactmodule.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
//TODO REVOIR
@Configuration
public class ConnectorArtifactProperties {

    @Value("${${instance.name}.${connector.type}.${connector.name}.feignName}")
    private String feignName;
    @Value("${${instance.name}.${connector.type}.${connector.name}.feignURL}")
    private String feignURL;

    public String getFeignName() {
        return feignName;
    }

    public void setFeignName(String feignName) {
        this.feignName = feignName;
    }

    public String getFeignURL() {
        return feignURL;
    }

    public void setFeignURL(String feignURL) {
        this.feignURL = feignURL;
    }
}
