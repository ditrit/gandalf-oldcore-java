package com.orness.gandalf.core.module.gitlabmodule.core;

import com.orness.gandalf.core.module.gitlabmodule.properties.ConnectorGitlabProperties;
import org.gitlab4j.api.GitLabApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectorGitlabConfiguration {

    private ConnectorGitlabProperties connectorGitlabProperties;

    @Autowired
    public ConnectorGitlabConfiguration(ConnectorGitlabProperties connectorGitlabProperties) {
        this.connectorGitlabProperties = connectorGitlabProperties;
    }

    //TODO ACCES TOKEN
    @Bean
    public GitLabApi gitLabApi() {
        return new GitLabApi(this.connectorGitlabProperties.getEndPointConnection(), "YOUR_ACCESS_TOKEN");
    }
}
