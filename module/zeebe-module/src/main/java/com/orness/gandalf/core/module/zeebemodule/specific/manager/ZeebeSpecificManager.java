package com.orness.gandalf.core.module.zeebemodule.specific.manager;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "specificManager")
@Profile(value = "zeebe-module")
public class ZeebeSpecificManager {
}
