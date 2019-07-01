package com.orness.gandalf.core.job.registerjob.register;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "orchestrator-service")
public interface RegisterFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/register")
    boolean register(@PathVariable("service") String service, @PathVariable("version") String version);
}
