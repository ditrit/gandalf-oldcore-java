package com.orness.gandalf.core.module.nexusmodule.normative.controller;

import com.orness.gandalf.core.module.artifactmodule.controller.ConnectorArtifactNormativeController;
import com.orness.gandalf.core.module.nexusmodule.normative.manager.NexusNormativeManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "commonWorkerCommand")
@Profile(value = "nexus-module")
public class NexusNormativeControllerConnector extends ConnectorArtifactNormativeController {

    private NexusNormativeManager nexusCommonManager;

    public NexusNormativeControllerConnector(NexusNormativeManager nexusCommonManager) {
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
