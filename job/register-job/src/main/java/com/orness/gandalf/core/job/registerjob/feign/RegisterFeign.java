package com.orness.gandalf.core.job.registerjob.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "orchestrator-service", url = "orchestrator-service.service.gandalf:10000")
public interface RegisterFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/register/{service}/{version}")
    boolean register(@PathVariable("service") String service, @PathVariable("version") String version);
}
