package com.orness.gandalf.core.module.versioncontrolmodule.manager;

import java.util.List;

public abstract class ConnectorVersionControlNormativeManager {

    public abstract void cloneProject(String uri, String path);

    public abstract void pull(String origin, String branch);

    public abstract void add(String path);

    public abstract void commit(String message);

    public abstract void push(String origin, String branch);

    public abstract void merge(String source, String destination);

    public abstract void checkout(String branch, boolean create);

    public abstract void tag(String tag);

    public abstract void log();

    public abstract Object execute(String apiName, String functionName, Object[] functionParameters);

    public abstract List listUsers();

    public abstract Object getUser(String username);

    public abstract List listProjects();

    public abstract Object getProjects(String path);

    public abstract List listBranches(String pathProject);

    public abstract Object getBranche(String pathProject, String name);

    public abstract List listMergeRequests(String pathProject);

    public abstract Object getMergeRequests(String pathProject, int id);

    public abstract List listCommits(String pathProject);

    public abstract Object getCommit(String pathProject, String sha);

    public abstract List listPages(String pathProject);

    public abstract Object getPage(String pathProject, String slug);

    public abstract List listHooks();

    public abstract List listIssues();

    public abstract Object getIssue(String pathProject, int id);

    public abstract List listGroups();

    public abstract Object getGroup(String path);

    public abstract List listEpics(String pathGroup);

    public abstract Object getEpic(String pathGroup, int id);

}
