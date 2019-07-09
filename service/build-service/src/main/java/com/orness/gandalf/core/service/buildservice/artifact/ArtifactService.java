package com.orness.gandalf.core.service.buildservice.artifact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.SCRIPT_DEPLOY_DIRECTORY;

@Service
public class ArtifactService {

    private ArtifactFeign artifactFeign;

    @Autowired
    public ArtifactService(ArtifactFeign artifactFeign) {
        this.artifactFeign = artifactFeign;

    }

    public boolean sendBuildToStorage(String projectName) {
        File file = new File(SCRIPT_DEPLOY_DIRECTORY + "/" + projectName + ".tar.gz");
        File conf = new File(SCRIPT_DEPLOY_DIRECTORY + "/" + projectName + "/" + projectName + ".ini");
        String version = null;
        MultipartFile MPfile = null;
        MultipartFile MPconf = null;
        try {
            MPfile = new GandalfMultipartFile(file.getName(), Files.readAllBytes(file.toPath()));
            MPconf = new GandalfMultipartFile(conf.getName(), Files.readAllBytes(conf.toPath()));
            version = Files.readAllLines(conf.toPath()).get(0).split("=")[1];
        } catch (IOException e) {
            e.printStackTrace();
        }

      /*  String version = null;
        FormData formDataFile = null;
        FormData formDataConf = null;
        try {
            version = Files.readAllLines(conf.toPath()).get(0).split("=")[1];
            formDataFile = new FormData("", projectName+".tar.gz", Files.readAllBytes(file.toPath()));
            formDataConf = new FormData("", projectName+".ini", Files.readAllBytes(conf.toPath()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(formDataFile.getFileName());
        System.out.println(formDataConf.getFileName());
        this.artifactFeign.uploadBuild(formDataFile, formDataConf, version);*/
        System.out.println("VERSION");
        System.out.println(version);


        this.artifactFeign.uploadBuildFile(version, MPfile);
        this.artifactFeign.uploadBuildConf(version, MPconf);

        return true;
    }
}
