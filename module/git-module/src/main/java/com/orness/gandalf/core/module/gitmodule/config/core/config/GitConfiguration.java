package com.orness.gandalf.core.module.gitmodule.config.core.config;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
//TODO USELESS BEAN
public class GitConfiguration {

    @Value("${gandalf.git.address}")
    private String gitAddress;

    @Bean
    public Git git() throws IOException {
        Git toto = new Git(null);
        Repository repository = GitHelper.openJGitRepository("");
        return new Git(repository);
    }
}
