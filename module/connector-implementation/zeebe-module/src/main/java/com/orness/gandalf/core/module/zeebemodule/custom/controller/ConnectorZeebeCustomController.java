package com.orness.gandalf.core.module.zeebemodule.custom.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "customController")
@Profile(value = "zeebe")
public class ConnectorZeebeCustomController {
}
