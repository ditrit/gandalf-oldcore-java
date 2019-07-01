package com.orness.gandalf.core.job.buildjob.artifact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;

import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.SCRIPT_DEPLOY_CONF_FILE;
import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.SCRIPT_DEPLOY_DIRECTORY;

@Service
public class ArtifactService {

    private ArtifactFeign artifactFeign;

    @Autowired
    public ArtifactService(ArtifactFeign artifactFeign) {
        this.artifactFeign = artifactFeign;

    }

    public boolean sendBuildToStorage(String projectName) {
        File file = new File(SCRIPT_DEPLOY_DIRECTORY + "/" + projectName + ".zip");
        File conf = new File(SCRIPT_DEPLOY_DIRECTORY + "/" + projectName + "/" + SCRIPT_DEPLOY_CONF_FILE);
        String version = null;
        try {
            version = Files.readAllLines(conf.toPath()).get(0).split("=")[1];
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.artifactFeign.uploadBuild(file, conf, version);

        return true;
    }
}
