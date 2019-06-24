package com.orness.gandalf.core.job.deployjob.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.SCRIPT_DEPLOY_DIRECTORY;

@Service
public class StorageService {

    private BuildStorageFeign buildStorageFeign;

    @Autowired
    public StorageService(BuildStorageFeign buildStorageFeign) {
        this.buildStorageFeign = buildStorageFeign;

    }

    public boolean getBuildFromStorage(String projectName) {
        try {
            File downloadFile = this.buildStorageFeign.downloadBuild(projectName).getBody().getFile();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
