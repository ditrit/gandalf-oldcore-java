package com.orness.gandalf.core.module.gitlabmodule.common.manager;

import com.orness.gandalf.core.module.gitmodule.config.common.manager.GitCommonManager;
import com.orness.gandalf.core.module.versioncontrolmodule.manager.VersionControlCommonManager;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.models.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GitlabCommonManager extends VersionControlCommonManager {

    private GitLabApi gitLabApi;
    private GitCommonManager gitCommonManager;

    public GitCommonManager getGitCommonManager() {
        return gitCommonManager;
    }

    @Autowired
    public GitlabCommonManager(GitLabApi gitLabApi, GitCommonManager gitCommonManager) {
        this.gitLabApi = gitLabApi;
        this.gitCommonManager = gitCommonManager;
    }


}
