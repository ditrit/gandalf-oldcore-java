package com.orness.gandalf.core.job.buildjob.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageService {

    private BuildStorageFeign buildStorageFeign;

    @Autowired
    public StorageService(BuildStorageFeign buildStorageFeign) {
        this.buildStorageFeign = buildStorageFeign;

    }

    public boolean sendBuildToStorage(String projectName) {
        //TODO SEND
        //buildStorageFeign.uploadBuild(new MockMultipartFile(name, originalFileName, contentType, content));
        return true;
    }
}
