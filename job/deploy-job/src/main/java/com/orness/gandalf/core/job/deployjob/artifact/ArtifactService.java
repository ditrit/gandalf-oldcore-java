package com.orness.gandalf.core.job.deployjob.artifact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ArtifactService {

    private ArtifactFeign artifactFeign;

    @Autowired
    public ArtifactService(ArtifactFeign artifactFeign) {
        this.artifactFeign = artifactFeign;

    }

    public boolean getBuildFromStorage(String projectName) {
        try {
            File downloadFile = this.artifactFeign.downloadBuild(projectName).getBody().getFile();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
