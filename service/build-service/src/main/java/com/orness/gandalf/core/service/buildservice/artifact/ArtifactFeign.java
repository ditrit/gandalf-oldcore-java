package com.orness.gandalf.core.service.buildservice.artifact;

import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@FeignClient(name = "artifact-service", url = "artifact-service.service.gandalf:10000", configuration = FeignConfig.class)
public interface ArtifactFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/upload/file")
    void uploadBuildFile(@Param("version") String version, @Param("file") MultipartFile file);

    @RequestMapping(method = RequestMethod.POST, value = "/upload/conf")
    void uploadBuildConf(@Param("version") String version, @Param("conf") MultipartFile conf);
}
