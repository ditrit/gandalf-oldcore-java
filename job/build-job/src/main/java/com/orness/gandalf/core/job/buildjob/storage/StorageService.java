package com.orness.gandalf.core.job.buildjob.storage;

import feign.form.FormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

import static com.orness.gandalf.core.module.constantmodule.bash.BashConstant.SCRIPT_DEPLOY_DIRECTORY;

@Service
public class StorageService {

    private BuildStorageFeign buildStorageFeign;

    @Autowired
    public StorageService(BuildStorageFeign buildStorageFeign) {
        this.buildStorageFeign = buildStorageFeign;

    }

    public boolean sendBuildToStorage(String projectName) {
        File file = new File(SCRIPT_DEPLOY_DIRECTORY + "/" + projectName + ".zip");
        MultipartFile mfile = null;

        //mfile = new MockMultipartFile(file.getName(), new FileInputStream(file));
        this.buildStorageFeign.uploadBuild(file);

        return true;
    }
}
