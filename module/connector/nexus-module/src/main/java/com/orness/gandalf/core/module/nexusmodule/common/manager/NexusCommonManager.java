package com.orness.gandalf.core.module.nexusmodule.common.manager;

import com.orness.gandalf.core.module.artifactmodule.manager.ArtifactCommonManager;
import com.orness.gandalf.core.module.nexusmodule.common.manager.feign.NexusFeign;
import org.sonatype.nexus.rest.model.ArtifactResolveResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "commonWorkerCommand")
@Profile(value = "nexus-module")
public class NexusCommonManager extends ArtifactCommonManager {

    private NexusFeign nexusFeign;

    @Autowired
    public NexusCommonManager(NexusFeign nexusFeign) {
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
    public Object downloadArtifact(Long id) {
        return this.nexusFeign.downloadComponent(id);
    }

    @Override
    public void deleteArtifact(Long id) {
        this.nexusFeign.deleteComponent(id);
    }
}
