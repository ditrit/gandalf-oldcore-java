package com.orness.gandalf.core.service.buildservice.controller;

import com.orness.gandalf.core.service.buildservice.archive.ArchiveService;
import com.orness.gandalf.core.service.buildservice.artifact.ArtifactService;
import com.orness.gandalf.core.service.buildservice.bash.BashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildController {

    private BashService bashService;
    private ArchiveService archiveService;
    private ArtifactService artifactService;

    @Autowired
    public BuildController(BashService bashService, ArchiveService archiveService, ArtifactService artifactService) {
        this.bashService = bashService;
        this.archiveService = archiveService;
        this.artifactService = artifactService;
    }

    @RequestMapping("/build/build/{projectUrl}")
    public boolean build(@PathVariable("projectUrl") String projectUrl) {
        boolean succes = true;
        //CLONE
        succes &= bashService.cloneProject(projectUrl);
        //MVN CLEAN INSTALL
        String projectFileName = projectUrl.split("/")[1];
        System.out.println(projectFileName);
        String projectName = projectFileName.substring(0, projectFileName.length()-4);
        System.out.println(projectName);
        succes &= bashService.buildProject(projectName);
        //TAR
        succes &= archiveService.zipArchive(projectName);
        //SEND TO STORAGE
        succes &= artifactService.sendBuildToStorage(projectName);
         return succes;
    }

}
