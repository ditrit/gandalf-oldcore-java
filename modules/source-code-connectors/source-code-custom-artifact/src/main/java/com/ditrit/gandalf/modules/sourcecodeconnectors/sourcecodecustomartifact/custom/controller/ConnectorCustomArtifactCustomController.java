package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.custom.controller;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.properties.ConnectorCustomArtifactProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

@RestController(value = "customController")
@ConditionalOnBean(ConnectorCustomArtifactProperties.class)
public class ConnectorCustomArtifactCustomController {
}