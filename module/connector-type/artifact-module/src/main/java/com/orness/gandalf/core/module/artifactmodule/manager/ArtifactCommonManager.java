package com.orness.gandalf.core.module.artifactmodule.manager;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public abstract class ArtifactCommonManager {

    public abstract List listArtifacts();

    public abstract void upload(MultipartFile artifact);

    public abstract Object download(Long id);

    public abstract void delete(Long id);

}
