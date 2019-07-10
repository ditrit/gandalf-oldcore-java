package com.orness.gandalf.core.job.buildjob.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "build-service", url = "build-service.service.gandalf:10000")
public interface BuildFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/build/{url}")
    boolean build(@PathVariable("url") String projectUrl);
}
