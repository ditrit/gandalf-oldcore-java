package com.orness.gandalf.core.module.h2module.properties;

import com.orness.gandalf.core.module.databasemodule.properties.ConnectorDatabaseProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile(value = "h2")
public class ConnectorH2Properties extends ConnectorDatabaseProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.";

}
