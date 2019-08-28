package com.orness.gandalf.core.module.gitlabmodule.normative.controller;

import com.orness.gandalf.core.module.gitlabmodule.normative.manager.GitlabCommonManager;
import com.orness.gandalf.core.module.versioncontrolmodule.controller.VersionControlCommonController;
import org.springframework.beans.factory.annotation.Autowired;

public class GitlabCommonController extends VersionControlCommonController {

    private GitlabCommonManager gitlabCommonManager;

    @Autowired
    public GitlabCommonController(GitlabCommonManager gitlabCommonManager) {
        this.gitlabCommonManager = gitlabCommonManager;
    }

    @Override
    public void cloneProject(String url) {
        this.gitlabCommonManager.cloneProject(url);
    }

    @Override
    public void pull(String origin, String branch) {
        this.gitlabCommonManager.pull(origin, branch);
    }

    @Override
    public void add(String path) {
        this.gitlabCommonManager.add(path);
    }

    @Override
    public void commit(String message) {
        this.gitlabCommonManager.cloneProject(message);
    }

    @Override
    public void push(String origin, String branch) {
        this.gitlabCommonManager.push(origin, branch);
    }

    @Override
    public void merge(String source, String destination) {
        this.gitlabCommonManager.merge(source, destination);
    }
}
