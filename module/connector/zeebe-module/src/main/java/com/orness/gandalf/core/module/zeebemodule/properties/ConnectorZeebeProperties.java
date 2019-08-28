package com.orness.gandalf.core.module.zeebemodule.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "zeebe-module")
@EnableConfigurationProperties
@ConfigurationProperties(prefix="zeebe")
public class ConnectorZeebeProperties {

    private String worker;

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }
}
