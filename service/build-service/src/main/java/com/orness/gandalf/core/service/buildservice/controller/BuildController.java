package com.orness.gandalf.core.service.buildservice.controller;

import com.orness.gandalf.core.service.buildservice.archive.ArchiveService;
import com.orness.gandalf.core.service.buildservice.artifact.ArtifactService;
import com.orness.gandalf.core.service.buildservice.bash.BashService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.SCRIPT_DEPLOY_DIRECTORY;

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

    @RequestMapping(method = RequestMethod.POST, value = "/build")
    public boolean build(@RequestBody String projectUrl) throws IOException, InterruptedException {
        System.out.println(projectUrl);
        boolean succes = true;
        //CLONE
        succes &= bashService.cloneProject(projectUrl);
        Thread.sleep(500);
        //MVN CLEAN INSTALL
        String projectFileName = projectUrl.split("/")[1];
        System.out.println(projectFileName);
        String projectName = projectFileName.substring(0, projectFileName.length()-4);
        System.out.println(projectName);
        succes &= bashService.buildProject(projectName);
        //TAR
        //succes &= archiveService.zipArchive(projectName);
        File conf = new File(SCRIPT_DEPLOY_DIRECTORY + "/" + projectName + "/" + projectName + ".ini");
        String version = Files.readAllLines(conf.toPath()).get(0).split("=")[1];
        String projectNameVersion = projectName + "_" + version;
        File conf_version = new File(SCRIPT_DEPLOY_DIRECTORY + "/" + projectNameVersion + ".ini");
        Files.copy(conf.toPath(), conf_version.toPath(), StandardCopyOption.REPLACE_EXISTING);
        conf_version.createNewFile();

        succes &= bashService.tarProject(projectNameVersion);

        //SEND TO STORAGE
        File file_version = new File(SCRIPT_DEPLOY_DIRECTORY + "/" + projectNameVersion + ".tar.gz");
        succes &= bashService.uploadProject(file_version);
        succes &= bashService.uploadConf(conf_version);
        //succes &= artifactService.sendBuildToStorage(projectName);
        //CLEAN
        FileUtils.deleteDirectory(new File(SCRIPT_DEPLOY_DIRECTORY + "/" + projectName));

         //return succes;
        return true;
    }

}
