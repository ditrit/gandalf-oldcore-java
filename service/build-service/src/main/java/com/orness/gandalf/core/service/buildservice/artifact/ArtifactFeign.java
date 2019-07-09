package com.orness.gandalf.core.service.buildservice.artifact;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import java.io.File;

@FeignClient(name = "artifact-service", url = "artifact-service.service.gandalf:10000", configuration = FeignConfig.class)
public interface ArtifactFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/upload/file")
    void uploadBuildFile(@RequestParam("version") String version, @RequestPart("file") File file);

    @RequestMapping(method = RequestMethod.POST, value = "/upload/conf")
    void uploadBuildConf(@RequestParam("version") String version, @RequestPart("conf") File conf);
}
