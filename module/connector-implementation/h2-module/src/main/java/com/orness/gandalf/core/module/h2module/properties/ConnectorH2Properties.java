package com.orness.gandalf.core.module.h2module.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
@Profile(value = "h2")
public class ConnectorH2Properties {

    @Value("${connector.name}")
    private String connectorName;
    @Value("${${connector.name}.worker}")
    private String worker;
    @Value("${${connector.name}.topics}")
    private List<String> topics;

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
