package com.orness.gandalf.core.module.versioncontrolmodule.manager;

import java.util.List;

public abstract class ConnectorVersionControlNormativeManager {

    public abstract void cloneProject(String payload);

    public abstract void pull(String payload);

    public abstract void add(String payload);

    public abstract void commit(String payload);

    public abstract void push(String payload);

    public abstract void merge(String payload);

    public abstract void checkout(String payload);

    public abstract void tag(String payload);

    public abstract void log();

    //public abstract Object execute(String payload);

    public abstract List listUsers();

    public abstract Object getUser(String payload);

    public abstract List listProjects();

    public abstract Object getProjects(String payload);

    public abstract List listBranches(String payload);

    public abstract Object getBranche(String payload);

    public abstract List listMergeRequests(String payload);

    public abstract Object getMergeRequests(String payload);

    public abstract List listCommits(String payload);

    public abstract Object getCommit(String payload);

    public abstract List listPages(String payload);

    public abstract Object getPage(String payload);

    public abstract List listHooks();

    public abstract List listIssues();

    public abstract Object getIssue(String payload);

    public abstract List listGroups();

    public abstract Object getGroup(String payload);

    public abstract List listEpics(String payload);

    public abstract Object getEpic(String payload);

}
