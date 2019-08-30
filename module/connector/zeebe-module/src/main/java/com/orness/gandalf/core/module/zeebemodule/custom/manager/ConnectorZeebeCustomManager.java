package com.orness.gandalf.core.module.zeebemodule.custom.manager;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "customManager")
@Profile(value = "zeebe")
public class ConnectorZeebeCustomManager {
}
