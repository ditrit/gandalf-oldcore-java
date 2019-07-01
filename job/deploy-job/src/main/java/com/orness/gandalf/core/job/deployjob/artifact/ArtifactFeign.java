package com.orness.gandalf.core.job.deployjob.artifact;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("build-artifact-service")
public interface ArtifactFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/download")
    ResponseEntity<Resource> downloadBuild(String filename);
}
