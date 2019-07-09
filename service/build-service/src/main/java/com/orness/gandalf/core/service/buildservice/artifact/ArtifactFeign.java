package com.orness.gandalf.core.service.buildservice.artifact;

import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@FeignClient(name = "artifact-service", url = "artifact-service.service.gandalf:10000", configuration = FeignConfig.class)
public interface ArtifactFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/upload/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void uploadBuildFile(@RequestPart("file") MultipartFile file, @RequestParam("version") String version);

    @RequestMapping(method = RequestMethod.POST, value = "/upload/conf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void uploadBuildConf(@RequestPart("conf") MultipartFile conf, @RequestParam("version") String version);
}
