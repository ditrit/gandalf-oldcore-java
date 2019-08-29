package com.orness.gandalf.core.module.gitlabmodule.normative.manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.orness.gandalf.core.module.gitmodule.config.normative.manager.ConnectorGitNormativeManager;
import com.orness.gandalf.core.module.versioncontrolmodule.manager.ConnectorVersionControlNormativeManager;
import org.gitlab4j.api.AbstractApi;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

@Component
public class ConnectorGitlabNormativeManager extends ConnectorVersionControlNormativeManager {

    private GitLabApi gitLabApi;
    private ConnectorGitNormativeManager gitCommonManager;
    private Gson mapper;

    public ConnectorGitNormativeManager getGitCommonManager() {
        return gitCommonManager;
    }

    @Autowired
    public ConnectorGitlabNormativeManager(GitLabApi gitLabApi, ConnectorGitNormativeManager gitCommonManager) {
        this.gitLabApi = gitLabApi;
        this.gitCommonManager = gitCommonManager;
        this.mapper = new Gson();
    }


    @Override
    public void cloneProject(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.gitCommonManager.cloneProject(jsonObject.get("uri").getAsString(), jsonObject.get("path").getAsString());
    }

    @Override
    public void pull(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.gitCommonManager.pull(jsonObject.get("origin").getAsString(), jsonObject.get("branch").getAsString());
    }

    @Override
    public void add(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.gitCommonManager.add(jsonObject.get("path").getAsString());
    }

    @Override
    public void commit(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.gitCommonManager.commit(jsonObject.get("message").getAsString());
    }

    @Override
    public void push(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.gitCommonManager.push(jsonObject.get("origin").getAsString(), jsonObject.get("branch").getAsString());
    }

    @Override
    public void merge(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.gitCommonManager.merge(jsonObject.get("source").getAsString(), jsonObject.get("destination").getAsString());
    }

    @Override
    public void checkout(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.gitCommonManager.checkout(jsonObject.get("branch").getAsString(), jsonObject.get("create").getAsBoolean());
    }

    @Override
    public void tag(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.gitCommonManager.tag(jsonObject.get("name").getAsString());
    }

    @Override
    public void log() {
        this.gitCommonManager.log();
    }

   /* @Override
    public Object execute(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
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
    }*/

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
    public Object getUser(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        try {
            return this.gitLabApi.getUserApi().getUser(jsonObject.get("username").getAsString());
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
    public Object getProjects(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        try {
            return this.gitLabApi.getProjectApi().getProject(jsonObject.get("path").getAsString());
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List listBranches(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        try {
            return this.gitLabApi.getRepositoryApi().getBranches(jsonObject.get("pathProject").getAsString());
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getBranche(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        try {
            return this.gitLabApi.getRepositoryApi().getBranch(jsonObject.get("pathProject").getAsString(), jsonObject.get("name").getAsString());
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List listMergeRequests(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        try {
            return this.gitLabApi.getMergeRequestApi().getMergeRequests(jsonObject.get("pathProject").getAsString());
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getMergeRequests(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        try {
            return this.gitLabApi.getMergeRequestApi().getMergeRequest(jsonObject.get("pathProject").getAsString(), jsonObject.get("id").getAsInt());
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List listCommits(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        try {
            return this.gitLabApi.getCommitsApi().getCommits(jsonObject.get("pathProject").getAsString());
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getCommit(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        try {
            return this.gitLabApi.getCommitsApi().getCommit(jsonObject.get("pathProject").getAsString(), jsonObject.get("sha").getAsString());
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List listPages(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        try {
            return this.gitLabApi.getWikisApi().getPages(jsonObject.get("pathProject").getAsString());
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getPage(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        try {
            return this.gitLabApi.getWikisApi().getPage(jsonObject.get("pathProject").getAsString(), jsonObject.get("slug").getAsString());
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
    public Object getIssue(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        try {
            return this.gitLabApi.getIssuesApi().getIssue(jsonObject.get("pathProject").getAsString(), jsonObject.get("id").getAsInt());
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
    public Object getGroup(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        try {
            return this.gitLabApi.getGroupApi().getGroup(jsonObject.get("path").getAsString());
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List listEpics(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        try {
            return this.gitLabApi.getEpicsApi().getEpics(jsonObject.get("pathGroup").getAsString());
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getEpic(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        try {
            return this.gitLabApi.getEpicsApi().getEpic(jsonObject.get("pathGroup").getAsString(), jsonObject.get("id").getAsInt());
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
        return null;
    }


}
