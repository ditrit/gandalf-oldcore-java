package com.orness.gandalf.core.module.jobzeebemodule.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "job-zeebe-module")
@EnableConfigurationProperties
@ConfigurationProperties(prefix="job.zeebe")
public class JobZeebeProperties {
}
