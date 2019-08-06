package com.orness.gandalf.core.module.kafkamodule.core.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "kafka-module")
@EnableConfigurationProperties
@ConfigurationProperties(prefix="kafka")
public class KafkaProperties {

    private String worker;
    private String group;

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
}
