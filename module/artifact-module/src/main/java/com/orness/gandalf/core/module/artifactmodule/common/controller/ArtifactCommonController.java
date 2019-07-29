package com.orness.gandalf.core.module.artifactmodule.common.controller;

import org.springframework.web.multipart.MultipartFile;

//TODO VOIR ANNOTATIONS
public abstract class ArtifactCommonController {

    public abstract void listArtifacts();

    public abstract void upload(MultipartFile artifact);

    public abstract Object download(String artifact);
}
