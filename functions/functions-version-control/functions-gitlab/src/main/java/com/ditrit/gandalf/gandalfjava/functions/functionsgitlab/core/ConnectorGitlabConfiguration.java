package com.ditrit.gandalf.gandalfjava.functions.functionsgitlab.core;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.properties.ConnectorGitlabProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnBean(ConnectorGitlabProperties.class)
public class ConnectorGitlabConfiguration {

    private ConnectorGitlabProperties connectorGitlabProperties;

    @Autowired
    public ConnectorGitlabConfiguration(ConnectorGitlabProperties connectorGitlabProperties) {
        this.connectorGitlabProperties = connectorGitlabProperties;
    }

    //TODO ACCES TOKEN
/*    @Bean
    public GitLabApi gitLabApi() {
        return new GitLabApi(this.connectorGitlabProperties.getEndPointConnection(), "YOUR_ACCESS_TOKEN");
    }*/
}
