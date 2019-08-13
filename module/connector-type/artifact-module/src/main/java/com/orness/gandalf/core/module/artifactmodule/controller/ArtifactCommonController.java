package com.orness.gandalf.core.module.artifactmodule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import static com.orness.gandalf.core.module.artifactmodule.constant.ArtifactConstant.*;

@RequestMapping(value = URL_CONTROLLER)
public abstract class ArtifactCommonController {

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_LIST)
    public abstract void listArtifacts();

    //TODO VOIR POUR /ARTIFACT ?
    @RequestMapping(method = RequestMethod.POST, value = URL_CONTROLLER_DOWNLOAD)
    public abstract void upload(MultipartFile artifact);

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_UPLOAD)
    public abstract Object download(String artifact);
}
