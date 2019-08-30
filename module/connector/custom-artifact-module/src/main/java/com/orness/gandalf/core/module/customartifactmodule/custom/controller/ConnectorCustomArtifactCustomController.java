package com.orness.gandalf.core.module.customartifactmodule.custom.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController(value = "customController")
@Profile(value = "custom-artifact")
public class ConnectorCustomArtifactCustomController {
}