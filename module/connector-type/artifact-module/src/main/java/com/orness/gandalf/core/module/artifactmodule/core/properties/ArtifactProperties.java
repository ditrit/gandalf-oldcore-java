package com.orness.gandalf.core.module.artifactmodule.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="artifact")
public class ArtifactProperties {

    private String feignName;
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