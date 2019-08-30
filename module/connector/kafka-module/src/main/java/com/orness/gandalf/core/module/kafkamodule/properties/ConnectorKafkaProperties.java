package com.orness.gandalf.core.module.kafkamodule.properties;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
@Profile(value = "kafka")
public class ConnectorKafkaProperties {

    @Value("${connector.name}")
    private String connectorName;
    @Value("${${connector.name}.synchronizeTopics}")
    private List<String> synchronizeTopics;
    @Value("${${connector.name}.worker}")
    private String worker;
    @Value("${${connector.name}.group}")
    private String group;
    @Value("${${connector.name}.topics}")
    private List<String> topics;

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<String> getSynchronizeTopics() {
        return synchronizeTopics;
    }

    public void setSynchronizeTopics(List<String> synchronizeTopics) {
        this.synchronizeTopics = synchronizeTopics;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
