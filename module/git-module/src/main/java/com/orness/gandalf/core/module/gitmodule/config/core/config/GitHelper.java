package com.orness.gandalf.core.module.gitmodule.config.core.config;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

//TODO REVOIR
public class GitHelper {

    public static Repository openJGitRepository(String path) throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        return builder
                .readEnvironment()
                .findGitDir(new File(path))
                .build();
    }

    public static Repository createNewRepository() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
 /*       // prepare a new folder
        File localPath = File.createTempFile("TestGitRepository", "");
        if(!localPath.delete()) {
            throw new IOException("Could not delete temporary file " + localPath);
        }
        // create the directory
        Repository repository = FileRepositoryBuilder.create(new File(localPath, ".git"));
        repository.create();

        return repository;*/
    }
}
