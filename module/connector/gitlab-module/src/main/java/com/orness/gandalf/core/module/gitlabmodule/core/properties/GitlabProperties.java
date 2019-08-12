package com.orness.gandalf.core.module.gitlabmodule.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "gitlab-module")
@EnableConfigurationProperties
@ConfigurationProperties(prefix="gitlab")
public class GitlabProperties {

    private String worker;

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }
}
