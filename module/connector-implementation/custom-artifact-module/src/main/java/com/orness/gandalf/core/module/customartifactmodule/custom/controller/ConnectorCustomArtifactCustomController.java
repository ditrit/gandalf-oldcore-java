package com.orness.gandalf.core.module.customartifactmodule.custom.controller;

import com.orness.gandalf.core.module.customartifactmodule.properties.ConnectorCustomArtifactProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

@RestController(value = "customController")
@ConditionalOnBean(ConnectorCustomArtifactProperties.class)
public class ConnectorCustomArtifactCustomController {
}