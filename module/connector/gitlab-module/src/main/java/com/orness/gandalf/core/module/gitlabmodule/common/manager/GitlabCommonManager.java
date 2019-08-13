package com.orness.gandalf.core.module.gitlabmodule.common.manager;

import com.orness.gandalf.core.module.gitmodule.config.common.manager.GitCommonManager;
import com.orness.gandalf.core.module.versioncontrolmodule.manager.VersionControlCommonManager;
import org.gitlab4j.api.AbstractApi;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

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


    @Override
    public void cloneProject(String uri, String path) {
        this.gitCommonManager.cloneProject(uri, path);
    }

    @Override
    public void pull(String origin, String branch) {
        this.gitCommonManager.pull(origin, branch);
    }

    @Override
    public void add(String path) {
        this.gitCommonManager.add(path);
    }

    @Override
    public void commit(String message) {
        this.gitCommonManager.commit(message);
    }

    @Override
    public void push(String origin, String branch) {
        this.gitCommonManager.push(origin, branch);
    }

    @Override
    public void merge(String source, String destination) {
        this.gitCommonManager.merge(source, destination);
    }

    @Override
    public void checkout(String branch, boolean create) {
        this.gitCommonManager.checkout(branch, create);
    }

    @Override
    public void tag(String name) {
        this.gitCommonManager.tag(name);
    }

    @Override
    public void log() {
        this.gitCommonManager.log();
    }

    @Override
    public Object execute(String apiName, String functionName, Object[] functionParameters) {
        Method[] apiArray = this.gitLabApi.getClass().getDeclaredMethods();
        AbstractApi api = null;
        for(Method apiMethod : apiArray) {
            if(isGetter(apiMethod) && apiMethod.getName().contains(apiName)) {
                try {
                    api = (AbstractApi) apiMethod.invoke(this.gitLabApi, null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        if(api != null) {
            Method[] functionArray = api.getClass().getDeclaredMethods();
            for(Method functionMethod : functionArray) {
                if(functionMethod.getName().contains(functionName)) {
                    try {
                        functionMethod.invoke(api, functionParameters[0]);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    private static boolean isGetter(Method method) {
        if (Modifier.isPublic(method.getModifiers()) &&
                method.getParameterTypes().length == 0) {
            if (method.getName().matches("^get[A-Z].*") &&
                    !method.getReturnType().equals(void.class))
                return true;
            if (method.getName().matches("^is[A-Z].*") &&
                    method.getReturnType().equals(boolean.class))
                return true;
        }
        return false;
    }

    @Override
    public List listUsers() {
        try {
            return this.gitLabApi.getUserApi().getActiveUsers();
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getUser(String username) {
        try {
            return this.gitLabApi.getUserApi().getUser(username);
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List listProjects() {
        try {
            return this.gitLabApi.getProjectApi().getProjects();
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getProjects(String path) {
        try {
            return this.gitLabApi.getProjectApi().getProject(path);
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List listBranches(String pathProject) {
        try {
            return this.gitLabApi.getRepositoryApi().getBranches(pathProject);
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getBranche(String pathProject, String name) {
        try {
            return this.gitLabApi.getRepositoryApi().getBranch(pathProject, name);
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List listMergeRequests(String pathProject) {
        try {
            return this.gitLabApi.getMergeRequestApi().getMergeRequests(pathProject);
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getMergeRequests(String pathProject, int id) {
        try {
            return this.gitLabApi.getMergeRequestApi().getMergeRequest(pathProject, id);
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List listCommits(String pathProject) {
        try {
            return this.gitLabApi.getCommitsApi().getCommits(pathProject);
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getCommit(String pathProject, String sha) {
        try {
            return this.gitLabApi.getCommitsApi().getCommit(pathProject, sha);
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List listPages(String pathProject) {
        try {
            return this.gitLabApi.getWikisApi().getPages(pathProject);
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getPage(String pathProject, String slug) {
        try {
            return this.gitLabApi.getWikisApi().getPage(pathProject, slug);
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List listHooks() {
        try {
            return this.gitLabApi.getSystemHooksApi().getSystemHooks();
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List listIssues() {
        try {
            return this.gitLabApi.getIssuesApi().getIssues();
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getIssue(String pathProject, int id) {
        try {
            return this.gitLabApi.getIssuesApi().getIssue(pathProject, id);
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List listGroups() {
        try {
            return this.gitLabApi.getGroupApi().getGroups();
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getGroup(String path) {
        try {
            return this.gitLabApi.getGroupApi().getGroup(path);
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List listEpics(String pathGroup) {
        try {
            return this.gitLabApi.getEpicsApi().getEpics(pathGroup);
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getEpic(String pathGroup, int id) {
        try {
            return this.gitLabApi.getEpicsApi().getEpic(pathGroup, id);
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }


}
