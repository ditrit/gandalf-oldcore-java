package com.ditrit.gandalf.modules.abstractconnectors.abstractartifact.manager;

import java.util.List;

public abstract class ConnectorArtifactNormativeManager {

    public abstract List listRepositories();

    public abstract List listArtifacts();

    public abstract void uploadArtifact(String payload);

    public abstract Object downloadArtifactById(String payload);

    public abstract Object downloadArtifact(String payload);

    public abstract void deleteArtifact(String payload);

}
