package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodeh2.properties;

import com.ditrit.gandalf.modules.abstractconnectors.abstractdatabase.properties.ConnectorDatabaseProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnExpression("#{'${${instance.name}.connectors.${connector.type}.${connector.name}.target.type}' == 'h2'}")
public class ConnectorH2Properties extends ConnectorDatabaseProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.endpoint.";

}
