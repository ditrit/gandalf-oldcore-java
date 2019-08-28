package com.orness.gandalf.core.module.gitmodule.config.normative.manager;

import com.orness.gandalf.core.module.gitmodule.config.core.config.GitService;
import com.orness.gandalf.core.module.versioncontrolmodule.manager.VersionControlCommonManager;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitCommonManager extends VersionControlCommonManager {

    private GitService gitService;

    @Autowired
    public GitCommonManager(GitService gitService) {
        this.gitService = gitService;
    }

    @Override
    public void cloneProject(String uri, String path) {
        this.gitService.cloneRepository(uri, path);
    }

    @Override
    public void pull(String origin, String branch) {
        PullCommand pull = this.gitService.getGit().pull();
        try {
            pull.setRemote(origin).setRemoteBranchName(branch).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void add(String path) {
        AddCommand add = this.gitService.getGit().add();
        try {
            add.addFilepattern(path).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void commit(String message) {
        CommitCommand commit = this.gitService.getGit().commit();
        try {
            commit.setMessage(message).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void push(String origin, String branch) {
        PushCommand push = this.gitService.getGit().push();
        try {
            push.setRemote(origin + ":" + branch);
            push.call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void merge(String source, String destination) {
        MergeCommand merge = this.gitService.getGit().merge();
        try {
            this.checkout(destination, false);
            Ref sourceRef = this.gitService.getGit().getRepository().findRef(source);
            merge.include(sourceRef);
            merge.call();
        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void checkout(String branch, boolean create) {
        CheckoutCommand checkout = this.gitService.getGit().checkout();
        checkout.setCreateBranch(create).setName(branch);
    }

    @Override
    public void tag(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void log() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
