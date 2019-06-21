package com.orness.gandalf.core.job.deployjob.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.SCRIPT_DEPLOY_DIRECTORY;

@Service
public class StorageService {

    private BuildStorageFeign buildStorageFeign;

    @Autowired
    public StorageService(BuildStorageFeign buildStorageFeign) {
        this.buildStorageFeign = buildStorageFeign;

    }

    public boolean getBuildFromStorage(String projectName) {
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(SCRIPT_DEPLOY_DIRECTORY + projectName);
            stream.write(this.buildStorageFeign.downloadBuild(projectName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
