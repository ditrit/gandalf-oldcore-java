package com.orness.gandalf.core.job.deployjob.storage;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("build-storage-service")
public interface BuildStorageFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/download")
    byte[] downloadBuild(String filename);
}
