package com.orness.gandalf.core.module.nexusmodule.common.manager;

import com.orness.gandalf.core.module.artifactmodule.manager.ArtifactCommonManager;
import com.orness.gandalf.core.module.nexusmodule.common.manager.feign.NexusFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
    public void upload(MultipartFile artifact) {

    }

    @Override
    public Object download(String artifact) {
        return null;
    }

    //TODO REVOIR
/*    @Override
    public void upload(MultipartFile artifact) {
        this.nexusFeign.uploadComponent(artifact);
    }

    @Override
    public Object download(String artifact) {
        this.nexusFeign.downloadComponent(artifact);
    }*/
}
