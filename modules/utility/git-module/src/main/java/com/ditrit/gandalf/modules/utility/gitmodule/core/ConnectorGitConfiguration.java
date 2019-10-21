package com.ditrit.gandalf.modules.utility.gitmodule.core;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ConnectorGitConfiguration {

/*    //TODO REVOIR
    @Value("${gandalf.git.address}")
    private String gitAddress;

    @Bean
    public Git git() throws IOException {
        Repository repository = GitHelper.openJGitRepository("");
        return new Git(repository);
    }*/
}
