package com.orness.gandalf.core.module.nexusmodule.normative.manager;

import com.orness.gandalf.core.module.artifactmodule.manager.ConnectorArtifactNormativeManager;
import com.orness.gandalf.core.module.nexusmodule.normative.manager.feign.NexusFeign;
import org.sonatype.nexus.rest.model.ArtifactResolveResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "commonWorkerCommand")
@Profile(value = "nexus-module")
public class ConnectorNexusNormativeManager extends ConnectorArtifactNormativeManager {

    private NexusFeign nexusFeign;

    @Autowired
    public ConnectorNexusNormativeManager(NexusFeign nexusFeign) {
        this.nexusFeign = nexusFeign;
    }

    @Override
    public List listRepositories() {
        return this.nexusFeign.listRepositories();
    }

    @Override
    public List listArtifacts() {
        return this.nexusFeign.listComponents();
    }

    @Override
    public void uploadArtifact(Object artifact) {
        this.nexusFeign.uploadComponent((ArtifactResolveResource) artifact);
    }

    @Override
    public Object downloadArtifactById(Long id) {
        return this.nexusFeign.downloadComponent(id);
    }

    @Override
    public Object downloadArtifact(Object artifact) {
        return null;
    }

    @Override
    public void deleteArtifact(Long id) {
        this.nexusFeign.deleteComponent(id);
    }
}
