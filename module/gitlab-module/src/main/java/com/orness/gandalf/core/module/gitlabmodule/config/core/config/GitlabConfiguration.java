package com.orness.gandalf.core.module.gitlabmodule.config.core.config;

import org.gitlab4j.api.GitLabApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitlabConfiguration {

    @Value("${gandalf.gitlab.address}")
    private String gitlabAddress;

    //TODO ACCESS TOKEN
    @Bean
    public GitLabApi gitLabApi() {
        return new GitLabApi(gitlabAddress, "YOUR_ACCESS_TOKEN");
    }
}
