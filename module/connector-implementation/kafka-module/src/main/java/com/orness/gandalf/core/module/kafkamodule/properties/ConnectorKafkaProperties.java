package com.orness.gandalf.core.module.kafkamodule.properties;

import com.orness.gandalf.core.module.busmodule.properties.ConnectorBusProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile(value = "kafka")
public class ConnectorKafkaProperties extends ConnectorBusProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.";

    @Value("${" + PROPERTIES_BASE + "synchronize.topics}")
    private List<String> synchronizeTopics;
    @Value("${" + PROPERTIES_BASE + "group}")
    private String group;

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

}
