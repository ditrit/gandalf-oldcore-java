package com.orness.gandalf.core.module.artifactmodule.manager;

import java.util.List;

public abstract class ConnectorArtifactNormativeManager {

    public abstract List listRepositories();

    public abstract List listArtifacts();

    public abstract void uploadArtifact(Object artifact);

    public abstract Object downloadArtifactById(Long id);

    public abstract Object downloadArtifact(Object artifact);

    public abstract void deleteArtifact(Long id);

}
