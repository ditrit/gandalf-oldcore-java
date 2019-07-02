package com.orness.gandalf.core.job.buildjob.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "feign-service")
public interface BuildFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/build/build")
    boolean build(@PathVariable("projectUrl") String projectUrl);
}
