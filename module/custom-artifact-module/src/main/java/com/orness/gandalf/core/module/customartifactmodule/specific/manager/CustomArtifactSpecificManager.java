package com.orness.gandalf.core.module.customartifactmodule.specific.manager;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "specificManager")
@Profile(value = "custom-artifact-module")
public class CustomArtifactSpecificManager {
}