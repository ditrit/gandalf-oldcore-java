package com.orness.gandalf.core.job.buildjob.storage;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileDescriptor;

@FeignClient(value = "build-storage-service", configuration = FeignConfig.class)
public interface BuildStorageFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    void uploadBuild(@RequestParam("file") File file);
}
