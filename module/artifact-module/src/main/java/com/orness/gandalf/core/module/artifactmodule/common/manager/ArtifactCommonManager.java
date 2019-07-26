package com.orness.gandalf.core.module.artifactmodule.common.manager;

public abstract class ArtifactCommonManager {

    public abstract void listArtifacts();

    public abstract void upload(String artifact);

    public abstract void download(String artifact);

}
