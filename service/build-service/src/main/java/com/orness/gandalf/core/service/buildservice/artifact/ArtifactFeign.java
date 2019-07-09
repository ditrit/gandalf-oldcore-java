package com.orness.gandalf.core.service.buildservice.artifact;

import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;


@FeignClient(name = "artifact-service", url = "artifact-service.service.gandalf:10000", configuration = FeignConfig.class)
public interface ArtifactFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/upload/file")
    void uploadBuildFile(@Param("file") MultipartFile file);

    @RequestMapping(method = RequestMethod.POST, value = "/upload/conf")
    void uploadBuildConf(@Param("conf") MultipartFile conf);
}
