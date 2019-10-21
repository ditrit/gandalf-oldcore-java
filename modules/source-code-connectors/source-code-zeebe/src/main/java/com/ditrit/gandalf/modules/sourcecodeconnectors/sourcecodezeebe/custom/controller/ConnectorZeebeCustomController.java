package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.custom.controller;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.properties.ConnectorZeebeProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "customController")
@ConditionalOnBean(ConnectorZeebeProperties.class)
public class ConnectorZeebeCustomController {
}
