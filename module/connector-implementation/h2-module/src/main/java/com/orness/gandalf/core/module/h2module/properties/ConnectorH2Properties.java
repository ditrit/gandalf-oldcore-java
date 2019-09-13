package com.orness.gandalf.core.module.h2module.properties;

import com.orness.gandalf.core.module.databasemodule.properties.ConnectorDatabaseProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value="target.type", havingValue = "h2")
public class ConnectorH2Properties extends ConnectorDatabaseProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.endpoint.";

}
