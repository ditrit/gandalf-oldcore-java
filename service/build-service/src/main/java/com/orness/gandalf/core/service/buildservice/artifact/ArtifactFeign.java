package com.orness.gandalf.core.service.buildservice.artifact;

import feign.Headers;
import feign.form.FormData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import java.io.File;

@FeignClient(name = "artifact-service", url = "artifact-service.service.gandalf:10000", configuration = FeignConfig.class)
public interface ArtifactFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    @Headers("Content-Type: multipart/form-data")
    void uploadBuild(@RequestParam("files") File[] files, @RequestPart("version") String version);
}
