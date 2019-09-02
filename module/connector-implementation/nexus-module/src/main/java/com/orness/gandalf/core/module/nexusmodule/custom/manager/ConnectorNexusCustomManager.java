package com.orness.gandalf.core.module.nexusmodule.custom.manager;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "customManager")
@Profile(value = "nexus")
public class ConnectorNexusCustomManager {
}
