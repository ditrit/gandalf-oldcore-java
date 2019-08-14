package com.orness.gandalf.core.module.artifactmodule.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static com.orness.gandalf.core.module.artifactmodule.constant.ArtifactConstant.*;

@RequestMapping(value = URL_CONTROLLER)
public abstract class ArtifactCommonController {

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_LIST_REPOSITORIES)
    public abstract List listRepositories();

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_LIST_ARTIFACTS)
    public abstract List listArtifacts();

    @RequestMapping(method = RequestMethod.POST, value = URL_CONTROLLER_UPLOAD_ARTIFACT)
    public abstract void uploadArtifact(@RequestBody Object artifact);

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_DOWNLOAD_ARTIFACT)
    public abstract Object downloadArtifact(@PathVariable("id") Long id);

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_DELETE_ARTIFACT)
    public abstract void deleteArtifact(@PathVariable("id") Long id);
}
