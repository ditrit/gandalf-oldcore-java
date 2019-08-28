package com.orness.gandalf.core.module.gitmodule.config.core;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ConnectorGitService {

    private Git git;

    public Git getGit() {
        return git;
    }

    public void createRepository(String path) {
        try {
            this.git = Git.init()
                    .setDirectory(new File(path))
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    public void loadRepository(String path) {
        Git git = new Git(this.openRepository(path));
    }

    private Repository openRepository(String path) {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        try {
            return builder
                    .readEnvironment()
                    .findGitDir(new File(path))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cloneRepository(String uri, String path) {
        try {
            this.git = Git.cloneRepository()
                    .setURI(uri)
                    .setDirectory(new File(path))
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }
}
