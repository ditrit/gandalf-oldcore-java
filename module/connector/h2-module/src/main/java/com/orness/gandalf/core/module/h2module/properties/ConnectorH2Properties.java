package com.orness.gandalf.core.module.h2module.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "h2-module")
@EnableConfigurationProperties
@ConfigurationProperties(prefix="h2")
public class ConnectorH2Properties {

    private String worker;

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }
}
