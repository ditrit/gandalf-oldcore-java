package com.orness.gandalf.core.module.gitmodule.config.normative.manager;

import com.orness.gandalf.core.module.gitmodule.config.core.ConnectorGitService;
import com.orness.gandalf.core.module.versioncontrolmodule.manager.ConnectorVersionControlNormativeManager;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ConnectorGitNormativeManager extends ConnectorVersionControlNormativeManager {

    private ConnectorGitService connectorGitService;

    @Autowired
    public ConnectorGitNormativeManager(ConnectorGitService connectorGitService) {
        this.connectorGitService = connectorGitService;
    }

    @Override
    public void cloneProject(String uri, String path) {
        this.connectorGitService.cloneRepository(uri, path);
    }

    @Override
    public void pull(String origin, String branch) {
        PullCommand pull = this.connectorGitService.getGit().pull();
        try {
            pull.setRemote(origin).setRemoteBranchName(branch).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void add(String path) {
        AddCommand add = this.connectorGitService.getGit().add();
        try {
            add.addFilepattern(path).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void commit(String message) {
        CommitCommand commit = this.connectorGitService.getGit().commit();
        try {
            commit.setMessage(message).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void push(String origin, String branch) {
        PushCommand push = this.connectorGitService.getGit().push();
        try {
            push.setRemote(origin + ":" + branch);
            push.call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void merge(String source, String destination) {
        MergeCommand merge = this.connectorGitService.getGit().merge();
        try {
            this.checkout(destination, false);
            Ref sourceRef = this.connectorGitService.getGit().getRepository().findRef(source);
            merge.include(sourceRef);
            merge.call();
        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void checkout(String branch, boolean create) {
        CheckoutCommand checkout = this.connectorGitService.getGit().checkout();
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

    @Override
    public Object execute(String apiName, String functionName, Object[] functionParameters) {
        return null;
    }

    @Override
    public List listUsers() {
        return null;
    }

    @Override
    public Object getUser(String username) {
        return null;
    }

    @Override
    public List listProjects() {
        return null;
    }

    @Override
    public Object getProjects(String path) {
        return null;
    }

    @Override
    public List listBranches(String pathProject) {
        return null;
    }

    @Override
    public Object getBranche(String pathProject, String name) {
        return null;
    }

    @Override
    public List listMergeRequests(String pathProject) {
        return null;
    }

    @Override
    public Object getMergeRequests(String pathProject, int id) {
        return null;
    }

    @Override
    public List listCommits(String pathProject) {
        return null;
    }

    @Override
    public Object getCommit(String pathProject, String sha) {
        return null;
    }

    @Override
    public List listPages(String pathProject) {
        return null;
    }

    @Override
    public Object getPage(String pathProject, String slug) {
        return null;
    }

    @Override
    public List listHooks() {
        return null;
    }

    @Override
    public List listIssues() {
        return null;
    }

    @Override
    public Object getIssue(String pathProject, int id) {
        return null;
    }

    @Override
    public List listGroups() {
        return null;
    }

    @Override
    public Object getGroup(String path) {
        return null;
    }

    @Override
    public List listEpics(String pathGroup) {
        return null;
    }

    @Override
    public Object getEpic(String pathGroup, int id) {
        return null;
    }
}
