package com.orness.gandalf.core.job.buildjob.storage;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("build-storage-service")
public interface BuildStorageFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    void uploadBuild(MultipartFile file);
}
