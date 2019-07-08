package com.orness.gandalf.core.service.buildservice.artifact;

import feign.form.FormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        FormData formDataFile = null;
        FormData formDataConf = null;
        try {
            version = Files.readAllLines(conf.toPath()).get(0).split("=")[1];
            formDataFile = new FormData("", projectName+".tar.gz", Files.readAllBytes(file.toPath()));
            formDataConf = new FormData("", projectName+".ini", Files.readAllBytes(conf.toPath()));

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.artifactFeign.uploadBuild(formDataFile, formDataConf, version);

        return true;
    }
}
