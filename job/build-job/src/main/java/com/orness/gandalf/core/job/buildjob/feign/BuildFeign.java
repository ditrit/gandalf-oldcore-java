package com.orness.gandalf.core.job.buildjob.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "build-service")
public interface BuildFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/build")
    boolean build(@RequestBody String projectUrl);
}
