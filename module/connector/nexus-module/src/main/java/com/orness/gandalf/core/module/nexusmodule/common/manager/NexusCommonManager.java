package com.orness.gandalf.core.module.nexusmodule.common.manager;

import com.orness.gandalf.core.module.artifactmodule.manager.ArtifactCommonManager;
import com.orness.gandalf.core.module.nexusmodule.common.manager.feign.NexusFeign;
import org.sonatype.nexus.client.core.subsystem.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class NexusCommonManager extends ArtifactCommonManager {

    private NexusFeign nexusFeign;

    @Autowired
    public NexusCommonManager(NexusFeign nexusFeign) {
        this.nexusFeign = nexusFeign;
    }

    @Override
    public List listArtifacts() {
        return this.nexusFeign.listComponents();
    }

    @Override
    public void upload(MultipartFile artifact) {
        Repository
    }



    @Override
    public Object download(String artifact) {
        return null;
    }

}
