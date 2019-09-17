package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.properties;

import com.ditrit.gandalf.modules.abstractconnectors.abstractbus.properties.ConnectorBusProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnExpression("#{'${${instance.name}.connectors.${connector.type}.${connector.name}.target.type}' == 'kafka'}")
public class ConnectorKafkaProperties extends ConnectorBusProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.endpoint.";

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
