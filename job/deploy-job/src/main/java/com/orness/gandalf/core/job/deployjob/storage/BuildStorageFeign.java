package com.orness.gandalf.core.job.deployjob.storage;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;

@FeignClient("build-storage-service")
public interface BuildStorageFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/download")
    ResponseEntity<Resource> downloadBuild(String filename);
}
