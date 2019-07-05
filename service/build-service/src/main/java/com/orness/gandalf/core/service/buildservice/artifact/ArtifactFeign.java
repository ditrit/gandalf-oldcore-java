package com.orness.gandalf.core.service.buildservice.artifact;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;

@FeignClient(name = "artifact-service", url = "artifact-service.service.gandalf:10000", configuration = FeignConfig.class)
public interface ArtifactFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    void uploadBuild(@RequestParam("file") File file, @RequestParam("conf") File conf, @RequestParam("version") String version);
}
