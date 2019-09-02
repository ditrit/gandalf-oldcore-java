package com.orness.gandalf.core.module.nexusmodule.normative.manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.orness.gandalf.core.module.artifactmodule.manager.ConnectorArtifactNormativeManager;
import com.orness.gandalf.core.module.nexusmodule.normative.manager.feign.NexusFeign;
import org.sonatype.nexus.rest.model.ArtifactResolveResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "normativeManager")
@Profile(value = "nexus")
public class ConnectorNexusNormativeManager extends ConnectorArtifactNormativeManager {

    private NexusFeign nexusFeign;
    private Gson mapper;

    @Autowired
    public ConnectorNexusNormativeManager(NexusFeign nexusFeign) {
        this.nexusFeign = nexusFeign;
        this.mapper = new Gson();
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
    public void uploadArtifact(String payload) {
        ArtifactResolveResource artifact = mapper.fromJson(payload, ArtifactResolveResource.class);
        this.nexusFeign.uploadComponent(artifact);
    }

    @Override
    public Object downloadArtifactById(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        return this.nexusFeign.downloadComponent(jsonObject.get("id").getAsLong());
    }

    @Override
    public Object downloadArtifact(String payload) {
        return null;
    }

    @Override
    public void deleteArtifact(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.nexusFeign.deleteComponent(jsonObject.get("id").getAsLong());
    }
}