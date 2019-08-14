package com.orness.gandalf.core.module.artifactmodule.manager;

import java.util.List;

public abstract class ArtifactCommonManager {

    public abstract List listRepositories();

    public abstract List listArtifacts();

    public abstract void uploadArtifact(Object artifact);

    public abstract Object downloadArtifact(Long id);

    public abstract void deleteArtifact(Long id);

}
