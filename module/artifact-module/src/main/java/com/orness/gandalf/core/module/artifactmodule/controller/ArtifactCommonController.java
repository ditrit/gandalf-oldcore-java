package com.orness.gandalf.core.module.artifactmodule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/artifact")
public abstract class ArtifactCommonController {

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public abstract void listArtifacts();

    //TODO VOIR POUR /ARTIFACT ?
    @RequestMapping(method = RequestMethod.POST, value = "/upload/artifact")
    public abstract void upload(MultipartFile artifact);

    @RequestMapping(method = RequestMethod.GET, value = "/download/{artifact}")
    public abstract Object download(String artifact);
}
