package com.orness.gandalf.core.module.kafkamodule.properties;

import com.orness.gandalf.core.module.busmodule.properties.ConnectorBusProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile(value = "kafka")
public class ConnectorKafkaProperties extends ConnectorBusProperties {

    @Value("${${connector.name}.${connector.type}.${connector.name}.${spring.profiles.active}.synchronizeTopics}")
    private List<String> synchronizeTopics;
    @Value("${${connector.name}.${connector.type}.${connector.name}.${spring.profiles.active}.group}")
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
