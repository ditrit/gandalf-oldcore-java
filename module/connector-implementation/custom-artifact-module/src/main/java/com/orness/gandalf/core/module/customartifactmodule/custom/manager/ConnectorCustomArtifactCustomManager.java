package com.orness.gandalf.core.module.customartifactmodule.custom.manager;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "customManager")
@Profile(value = "custom-artifact")
public class ConnectorCustomArtifactCustomManager {
}
