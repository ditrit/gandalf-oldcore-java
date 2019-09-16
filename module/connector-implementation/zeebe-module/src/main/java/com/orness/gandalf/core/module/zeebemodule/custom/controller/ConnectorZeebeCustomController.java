package com.orness.gandalf.core.module.zeebemodule.custom.controller;

import com.orness.gandalf.core.module.zeebemodule.properties.ConnectorZeebeProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "customController")
@ConditionalOnBean(ConnectorZeebeProperties.class)
public class ConnectorZeebeCustomController {
}
