package com.orness.gandalf.core.module.artifactmodule.common.manager;

import org.springframework.web.multipart.MultipartFile;

public abstract class ArtifactCommonManager {

    public abstract void listArtifacts();

    public abstract void upload(MultipartFile artifact);

    public abstract Object download(String artifact);

}
