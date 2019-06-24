package com.orness.gandalf.core.job.buildjob.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.SCRIPT_DEPLOY_DIRECTORY;

@Service
public class StorageService {

    private BuildStorageFeign buildStorageFeign;

    @Autowired
    public StorageService(BuildStorageFeign buildStorageFeign) {
        this.buildStorageFeign = buildStorageFeign;

    }

    public boolean sendBuildToStorage(String projectName) {
        File file = new File(SCRIPT_DEPLOY_DIRECTORY + projectName + ".zip");
        buildStorageFeign.uploadBuild(file);
        return true;
    }
}
