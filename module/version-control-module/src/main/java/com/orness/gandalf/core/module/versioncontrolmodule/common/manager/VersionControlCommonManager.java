package com.orness.gandalf.core.module.versioncontrolmodule.common.manager;

public abstract class VersionControlCommonManager {

    public abstract void cloneProject(String url);

    public abstract void pull(String origin, String branch);

    public abstract void add(String path);

    public abstract void commit(String message);

    public abstract void push(String origin, String branch);

    public abstract void merge(String source, String destination);

}
