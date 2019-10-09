package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.normative.manager;

import com.ditrit.gandalf.core.clientcore.worker.WorkerClient;
import com.ditrit.gandalf.library.gandalfworkerclient.LibraryWorkerClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.properties.ConnectorGitlabProperties;
import com.ditrit.gandalf.modules.abstractconnectors.abstractversioncontrol.manager.ConnectorVersionControlNormativeManager;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

//TODO REVOIR
@Component(value = "normativeManager")
//@ComponentScan(basePackages = {"com.ditrit.gandalf.core.clientcore.worker"})
@ConditionalOnBean(ConnectorGitlabProperties.class)
public class ConnectorGitlabNormativeManager extends ConnectorVersionControlNormativeManager {

    private GitLabApi gitLabApi;
    //private ConnectorGitNormativeManager gitCommonManager;
    private LibraryWorkerClient libraryWorkerClient;
    private Gson mapper;
    private JsonObject jsonObject;

/*    public ConnectorGitNormativeManager getGitCommonManager() {
        return gitCommonManager;
    }

    @Autowired
    public ConnectorGitlabNormativeManager(GitLabApi gitLabApi, ConnectorGitNormativeManager gitCommonManager, GandalfClient gandalfClient) {
        this.gitLabApi = gitLabApi;
        this.gitCommonManager = gitCommonManager;
        this.gandalfClient = gandalfClient;
        this.mapper = new Gson();
    }*/

    @Autowired
    public ConnectorGitlabNormativeManager(LibraryWorkerClient libraryWorkerClient) {
        this.libraryWorkerClient = libraryWorkerClient;
        this.mapper = new Gson();
    }

    public void hookMerge(String hook) {

        jsonObject = this.mapper.fromJson(hook, JsonObject.class);
        JsonObject jsonObjectProject = jsonObject.get("project").getAsJsonObject();
        JsonObject jsonObjectRepository = jsonObject.get("repository").getAsJsonObject();
        JsonObject jsonObjectAttributes = jsonObject.get("object_attributes").getAsJsonObject();

        JsonObject payload = new JsonObject();
        StringBuilder topic = new StringBuilder(jsonObjectRepository.get("name").getAsString()).append(".").append(jsonObjectProject.get("name").getAsString());
        payload.addProperty("correlation_key", topic.toString());
        payload.addProperty("project_url", jsonObjectProject.get("git_http_url").getAsString());
        payload.addProperty("project_name", jsonObjectProject.get("name").getAsString());
        System.out.println("SEND");
        System.out.println(topic.toString());
        System.out.println("HOOK_MERGE");
        System.out.println(payload.toString());
        //this.gandalfClient.sendCommand("toto", "toto", "toto", "toto", "toto");
        this.libraryWorkerClient.getWorkerClient().sendEvent(topic.toString(), "HOOK_MERGE", "5", payload.toString());

    }

    @Override
    public void cloneProject(String payload) {
        //JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //this.gitCommonManager.cloneProject(jsonObject.get("uri").getAsString(), jsonObject.get("path").getAsString());
    }

    @Override
    public void pull(String payload) {
        //JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //this.gitCommonManager.pull(jsonObject.get("origin").getAsString(), jsonObject.get("branch").getAsString());
    }

    @Override
    public void add(String payload) {
        //JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //this.gitCommonManager.add(jsonObject.get("path").getAsString());
    }

    @Override
    public void commit(String payload) {
        //JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //this.gitCommonManager.commit(jsonObject.get("message").getAsString());
    }

    @Override
    public void push(String payload) {
        //JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //this.gitCommonManager.push(jsonObject.get("origin").getAsString(), jsonObject.get("branch").getAsString());
    }

    @Override
    public void merge(String payload) {
        //JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //this.gitCommonManager.merge(jsonObject.get("source").getAsString(), jsonObject.get("destination").getAsString());
    }

    @Override
    public void checkout(String payload) {
        //JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //this.gitCommonManager.checkout(jsonObject.get("branch").getAsString(), jsonObject.get("create").getAsBoolean());
    }

    @Override
    public void tag(String payload) {
        //JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        //this.gitCommonManager.tag(jsonObject.get("name").getAsString());
    }

    @Override
    public void log() {
        //this.gitCommonManager.log();
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

    public void addHook(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
/*        try {
            //TODO REVOIR
            //this.gitLabApi.getSystemHooksApi().addSystemHook("", "", "", "", "");
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }*/
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
