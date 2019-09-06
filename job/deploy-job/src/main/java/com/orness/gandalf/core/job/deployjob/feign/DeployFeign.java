package com.orness.gandalf.core.job.deployjob.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//TODO REVOIR
@FeignClient(name = "orchestrator-service", url = "orchestrator-service.service.gandalf:10000")
public interface DeployFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/orchestrator/deploy/{service}")
    boolean deploy(@PathVariable("service") String service);
}
