package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegandalf.properties;

import com.ditrit.gandalf.core.connectorcore.properties.ConnectorProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ConnectorGandalfProperties extends ConnectorProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.";

    //@Value("${" + PROPERTIES_BASE + "topics}")
    private List<String> topics;

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
