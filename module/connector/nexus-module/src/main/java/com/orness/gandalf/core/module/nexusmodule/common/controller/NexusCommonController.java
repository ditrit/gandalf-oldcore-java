package com.orness.gandalf.core.module.nexusmodule.common.controller;

import com.orness.gandalf.core.module.artifactmodule.controller.ArtifactCommonController;
import com.orness.gandalf.core.module.nexusmodule.common.manager.NexusCommonManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "commonWorkerCommand")
@Profile(value = "nexus-module")
public class NexusCommonController extends ArtifactCommonController {

    private NexusCommonManager nexusCommonManager;

    public NexusCommonController(NexusCommonManager nexusCommonManager) {
        this.nexusCommonManager = nexusCommonManager;
    }

    @Override
    public List listRepositories() {
        return this.nexusCommonManager.listRepositories();
    }

    @Override
    public List listArtifacts() {
        return this.nexusCommonManager.listArtifacts();
    }

    @Override
    public void uploadArtifact(Object artifact) {
        this.nexusCommonManager.uploadArtifact(artifact);
    }

    @Override
    public Object downloadArtifact(Long id) {
        return this.nexusCommonManager.downloadArtifact(id);
    }

    @Override
    public void deleteArtifact(Long id) {
        this.nexusCommonManager.deleteArtifact(id);
    }
}
