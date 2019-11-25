package com.ditrit.gandalf.gandalfjava.services.buildservice.controller;

import com.ditrit.gandalf.gandalfjava.services.buildservice.archive.ArchiveService;
import com.ditrit.gandalf.gandalfjava.services.buildservice.artifact.ArtifactService;
import com.ditrit.gandalf.gandalfjava.services.buildservice.constant.BuildConstant;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ditrit.gandalf.gandalfjava.services.buildservice.bash.BashService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@RestController
public class BuildController {

    private BashService bashService;
    private ArchiveService archiveService;
    private ArtifactService artifactService;
    private Gson mapper;

    @Autowired
    public BuildController(BashService bashService, ArchiveService archiveService, ArtifactService artifactService) {
        this.bashService = bashService;
        this.archiveService = archiveService;
        this.artifactService = artifactService;
        this.mapper = new Gson();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/build")
    public String build(@RequestBody String projectValues) throws IOException {
        JsonObject request = this.mapper.fromJson(projectValues, JsonObject.class);
        String projectName = request.get("name").getAsString();
        String projectUrl = request.get("url").getAsString();
        JsonObject response = new JsonObject();
        boolean succes = true;
        //CLONE
        succes &= bashService.cloneProject(projectUrl);
        System.out.println("CLONE");
        System.out.println(succes);

        //Thread.sleep(500);
        //MVN CLEAN INSTALL
        //String projectFileName = projectUrl.split("/")[1];
        //System.out.println(projectFileName);
        //String projectName = projectFileName.substring(0, projectFileName.length()-4);
        succes &= bashService.buildProject(projectName);
        System.out.println("BUILD");
        System.out.println(succes);

        //Thread.sleep(1000);
        //TAR
        //succes &= archiveService.zipArchive(projectName);
        File conf = new File(BuildConstant.SCRIPT_DEPLOY_DIRECTORY + "/" + projectName + "/" + projectName + ".ini");
        String version = Files.readAllLines(conf.toPath()).get(0).split("=")[1];
        String projectNameVersion = projectName + "_" + version;
        File confNameversion = new File(BuildConstant.SCRIPT_DEPLOY_DIRECTORY + "/" + projectNameVersion + ".ini");
        Files.copy(conf.toPath(), confNameversion.toPath(), StandardCopyOption.REPLACE_EXISTING);
        confNameversion.createNewFile();

        succes &= bashService.tarProject(projectName, projectNameVersion);
        System.out.println("TAR");
        System.out.println(succes);

        //SEND TO STORAGE
        File file_version = new File(BuildConstant.SCRIPT_DEPLOY_DIRECTORY + "/" + projectNameVersion + ".tar.gz");
        JsonObject result = bashService.uploadProject(file_version);
        succes &= result.get("result").getAsBoolean();
        String url_project = result.get("url").getAsString();

        System.out.println("UPLOAD FILE");
        System.out.println(succes);

        result = bashService.uploadConf(confNameversion);
        succes &= result.get("result").getAsBoolean();
        String url_conf = result.get("url").getAsString();

        System.out.println("UPLOAD CONF");
        System.out.println(succes);
        //succes &= artifactService.sendBuildToStorage(projectName);
        //CLEAN
        FileUtils.deleteDirectory(new File(BuildConstant.SCRIPT_DEPLOY_DIRECTORY + "/" + projectName));

        response.addProperty("version", version);
        response.addProperty("project_url", url_project);
        response.addProperty("conf_url", url_conf);
        response.addProperty("succes", succes);
        System.out.println("REPONSE");
        System.out.println(response);
        //return succes;
        return response.toString();
    }

}
