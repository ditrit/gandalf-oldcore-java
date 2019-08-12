package com.orness.gandalf.core.module.versioncontrolmodule.manager;

public abstract class VersionControlCommonManager {

    public abstract void cloneProject(String uri, String path);

    public abstract void pull(String origin, String branch);

    public abstract void add(String path);

    public abstract void commit(String message);

    public abstract void push(String origin, String branch);

    public abstract void merge(String source, String destination);

    public abstract void checkout(String branch, boolean create);

    public abstract void tag(String tag);

    public abstract void log();
}
