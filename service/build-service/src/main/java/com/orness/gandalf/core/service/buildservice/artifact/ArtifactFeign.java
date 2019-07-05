package com.orness.gandalf.core.service.buildservice.artifact;

import feign.Param;
import feign.form.FormData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "artifact-service", url = "artifact-service.service.gandalf:10000", configuration = FeignConfig.class)
public interface ArtifactFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    void uploadBuild(@Param("file") FormData file, @Param("conf") FormData conf, @Param("version") String version);
}
