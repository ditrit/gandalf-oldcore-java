package com.orness.gandalf.core.module.nexusmodule.common.manager;

import com.orness.gandalf.core.module.artifactmodule.manager.ArtifactCommonManager;
import com.orness.gandalf.core.module.nexusmodule.common.manager.feign.NexusFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NexusCommonManager extends ArtifactCommonManager {

    private NexusFeign nexusFeign;

    @Autowired
    public NexusCommonManager(NexusFeign nexusFeign) {
        this.nexusFeign = nexusFeign;
    }

    @Override
    public void listArtifacts() {
        this.nexusFeign.listComponents();
    }

    @Override
    public void upload(String artifact) {
        this.nexusFeign.uploadComponent(artifact);
    }

    @Override
    public void download(String id) {
        this.nexusFeign.downloadComponent(id);
    }
}
