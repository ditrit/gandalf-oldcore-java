package com.ditrit.gandalf.gandalfjava.core.workercore.properties;

import com.ditrit.gandalf.gandalfjava.core.connectorcore.properties.ConnectorProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnExpression("#{'${${instance.name}.connectors.${connector.type}.${connector.name}.target.type}' == 'kafka'}")
public class FunctionKafkaProperties extends ConnectorProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.endpoint.";

    @Value("${" + PROPERTIES_BASE + "synchronize.topics:test}")
    private List<String> synchronizeTopics;
    @Value("${" + PROPERTIES_BASE + "group:kafka}")
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
